package com.operator.charge_config.service.impl;

import com.operator.charge_config.base.ApiResponse;
import com.operator.charge_config.enumeration.InboxStatus;
import com.operator.charge_config.model.ChargeFailureLog;
import com.operator.charge_config.model.ChargeSuccessLog;
import com.operator.charge_config.model.Inbox;
import com.operator.charge_config.repository.ChargeFailureLogRepository;
import com.operator.charge_config.service.ChargeFailureLogService;
import com.operator.charge_config.service.InboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargeFailureLogServiceImpl implements ChargeFailureLogService {

    @Autowired
    private ChargeFailureLogRepository chargeFailureLogRepository;
    @Autowired
    private InboxService inboxService;


    @Override
    public ChargeFailureLog save(Inbox inbox, ApiResponse apiResponse) {
        ChargeFailureLog chargeFailureLog = mapInboxToChargeFailureLog(inbox, apiResponse);
        inboxService.updateStatus(inbox, InboxStatus.FAILED.getCode());
        return chargeFailureLogRepository.save(chargeFailureLog);
    }

    private ChargeFailureLog mapInboxToChargeFailureLog(Inbox inbox, ApiResponse apiResponse) {
        return ChargeFailureLog.builder()
                .gameName(inbox.getGameName())
                .msisdn(inbox.getMsisdn())
                .keyword(inbox.getKeyword())
                .operator(inbox.getOperator())
                .smsId(inbox.getId())
                .shortCode(inbox.getShortCode())
                .transactionId(inbox.getTransactionId())
                .statusCode(apiResponse.getStatusCode())
                .message(apiResponse.getMessage())
                .build();
    }
}
