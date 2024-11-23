package com.operator.charge_config.service.impl;

import com.operator.charge_config.enumeration.InboxStatus;
import com.operator.charge_config.model.ChargeSuccessLog;
import com.operator.charge_config.model.Inbox;
import com.operator.charge_config.repository.ChargeSuccessLogRepository;
import com.operator.charge_config.service.ChargeSuccessLogService;
import com.operator.charge_config.service.InboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargeSuccessLogServiceImpl implements ChargeSuccessLogService {

    @Autowired
    private InboxService inboxService;

    @Autowired
    private ChargeSuccessLogRepository chargeSuccessLogRepository;


    @Override
    public ChargeSuccessLog save(Inbox inbox) {
        ChargeSuccessLog chargeSuccessLog = mapInboxToChargeSuccessLog(inbox);
        inboxService.updateStatus(inbox, InboxStatus.SUCCESS.getCode());
        return chargeSuccessLogRepository.save(chargeSuccessLog);
    }

    private ChargeSuccessLog mapInboxToChargeSuccessLog(Inbox inbox) {
        return ChargeSuccessLog.builder()
                .gameName(inbox.getGameName())
                .msisdn(inbox.getMsisdn())
                .keyword(inbox.getKeyword())
                .operator(inbox.getOperator())
                .smsId(inbox.getId())
                .shortCode(inbox.getShortCode())
                .transactionId(inbox.getTransactionId())
                .build();
    }
}
