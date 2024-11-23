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
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private final ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void retrieveContents() {
        System.out.println("Starting content retrieval process using virtual threads...");
        List<Contents> contentsList = gatewayService.getContents();
        for (Contents content : contentsList) {
            String sms = content.getSms().trim();
            List<String> words = Arrays.asList(sms.split(" "));
            String keyword = words.get(0);
            String gameName = words.get(1);

            processContent(content, keyword, gameName);
        }
        System.out.println("Process ended");
    }

    private void processContent(Contents content, String keyword, String gameName) {
        try {
            System.out.println("Enter for processing contents");
            Inbox inbox = inboxService.save(content, keyword, gameName);
            if (keywordDetailsService.findByKeyword(keyword)) {
                System.out.println("Keyword Found");
                UnlockCodeRequestDto unlockCodeRequestDto = buildUnlockCodeRequest(content, gameName, keyword);
                UnlockCodeResponse unlockCodeResponse = gatewayService.unlockCode(unlockCodeRequestDto);

                ChargeConfig chargeConfig = chargeConfigService.findByOperator(unlockCodeResponse.getOperator());
                ServiceChargeRequestDto serviceChargeRequestDto = buildServiceChargeRequest(unlockCodeResponse, chargeConfig.getChargeCode());
                ServiceChargeResponse serviceChargeResponse = gatewayService.performCharging(serviceChargeRequestDto);

                if (serviceChargeResponse.getStatusCode().equals(200)) {
                    System.out.println("Success log");
                    chargeSuccessLogService.save(inbox);
                } else {
                    System.out.println("Failure log");
                    chargeFailureLogService.save(inbox);
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing content: " + e.getMessage());
        }
    }

    @PostConstruct
    public void initiateRetrieval() {
        virtualThreadExecutor.submit(this::retrieveContents);
    }

    private UnlockCodeRequestDto buildUnlockCodeRequest(Contents contents, String gameName, String keyword) {
        return UnlockCodeRequestDto.builder()
                .transactionId(contents.getTransactionId())
                .shortCode(contents.getShortCode())
                .msisdn(contents.getMsisdn())
                .operator(contents.getOperator())
                .gameName(gameName)
                .keyword(keyword)
                .build();
    }

    private ServiceChargeRequestDto buildServiceChargeRequest(UnlockCodeResponse unlockCodeResponse, String chargeCode) {
        return ServiceChargeRequestDto.builder()
                .transactionId(unlockCodeResponse.getTransactionId())
                .chargeCode(chargeCode)
                .msisdn(unlockCodeResponse.getMsisdn())
                .shortCode(unlockCodeResponse.getShortCode())
                .operator(unlockCodeResponse.getOperator())
                .build();
    }

}
