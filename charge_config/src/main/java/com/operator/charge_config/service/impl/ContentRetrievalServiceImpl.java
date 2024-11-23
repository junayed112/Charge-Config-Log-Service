package com.operator.charge_config.service.impl;

import com.operator.charge_config.dto.request.ServiceChargeRequestDto;
import com.operator.charge_config.dto.request.UnlockCodeRequestDto;
import com.operator.charge_config.dto.response.ServiceChargeResponse;
import com.operator.charge_config.dto.response.UnlockCodeResponse;
import com.operator.charge_config.external.Gateway;
import com.operator.charge_config.model.ChargeConfig;
import com.operator.charge_config.model.Inbox;
import com.operator.charge_config.service.*;
import com.operator.charge_config.util.Contents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ContentRetrievalServiceImpl implements ContentRetrievalService {

    @Autowired
    private Gateway gatewayService;
    @Autowired
    private KeywordDetailsService keywordDetailsService;
    @Autowired
    private ChargeConfigService chargeConfigService;
    @Autowired
    private InboxService inboxService;
    @Autowired
    private ChargeFailureLogService chargeFailureLogService;
    @Autowired
    private ChargeSuccessLogService chargeSuccessLogService;

    @Override
    public void retrieveContents() {
        List<Contents> contentsList = gatewayService.getContents();
        for (Contents content: contentsList) {
            String sms = new StringBuilder(content.getSms()).toString();
            sms = sms.trim();
            List<String> words = Arrays.asList(sms.split(" "));
            String keyword = words.get(0);
            String gameName = words.get(1);
            Inbox inbox = inboxService.save(content, keyword, gameName);
            UnlockCodeRequestDto unlockCodeRequestDto = new UnlockCodeRequestDto();
            UnlockCodeResponse unlockCodeResponse = new UnlockCodeResponse();
            if(keywordDetailsService.findByKeyword(keyword)){
                unlockCodeRequestDto = createUnlockCodeRequestBody(content, gameName, keyword);
                unlockCodeResponse = gatewayService.unlockCode(unlockCodeRequestDto);
                ChargeConfig chargeConfig = chargeConfigService.findByOperator(unlockCodeResponse.getOperator());
                ServiceChargeRequestDto serviceChargeRequestDto = createServiceChargeRequestBody(unlockCodeResponse, chargeConfig.getChargeCode());
                ServiceChargeResponse serviceChargeResponse = gatewayService.performCharging(serviceChargeRequestDto);
                if(serviceChargeResponse.getStatusCode().equals(200)){
                    chargeSuccessLogService.save(inbox);
                }
                else{
                    chargeFailureLogService.save(inbox);
                }

            }
        }
    }

    private UnlockCodeRequestDto createUnlockCodeRequestBody(Contents contents, String gameName, String keyword){
        UnlockCodeRequestDto unlockCodeRequestDto = new UnlockCodeRequestDto();
        unlockCodeRequestDto.setTransactionId(contents.getTransactionId());
        unlockCodeRequestDto.setShortCode(contents.getShortCode());
        unlockCodeRequestDto.setMsisdn(contents.getMsisdn());
        unlockCodeRequestDto.setOperator(contents.getOperator());
        unlockCodeRequestDto.setGameName(gameName);
        unlockCodeRequestDto.setKeyword(keyword);
        return unlockCodeRequestDto;

    }

    private ServiceChargeRequestDto createServiceChargeRequestBody(UnlockCodeResponse unlockCodeResponse, String chargeCode){
        ServiceChargeRequestDto serviceChargeRequestDto = new ServiceChargeRequestDto();
        serviceChargeRequestDto.setTransactionId(unlockCodeResponse.getTransactionId());
        serviceChargeRequestDto.setChargeCode(chargeCode);
        serviceChargeRequestDto.setMsisdn(unlockCodeResponse.getMsisdn());
        serviceChargeRequestDto.setShortCode(unlockCodeResponse.getShortCode());
        serviceChargeRequestDto.setOperator(unlockCodeResponse.getOperator());

        return serviceChargeRequestDto;
    }
}
