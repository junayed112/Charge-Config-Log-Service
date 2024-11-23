package com.operator.charge_config.service.impl;

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
    public ChargeFailureLog save(Inbox inbox) {
        ChargeFailureLog chargeFailureLog = new ChargeFailureLog();
        chargeFailureLog.setGameName(inbox.getGameName());
        chargeFailureLog.setMsisdn(inbox.getMsisdn());
        chargeFailureLog.setKeyword(inbox.getKeyword());
        chargeFailureLog.setOperator(inbox.getOperator());
        chargeFailureLog.setSmsId(inbox.getId());
        chargeFailureLog.setShortCode(inbox.getShortCode());
        chargeFailureLog.setTransactionId(inbox.getTransactionId());
        inboxService.updateStatus(inbox, "F");
        return chargeFailureLogRepository.save(chargeFailureLog);
    }
}
