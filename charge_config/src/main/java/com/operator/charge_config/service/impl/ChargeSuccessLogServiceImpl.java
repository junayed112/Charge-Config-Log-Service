package com.operator.charge_config.service.impl;

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
        ChargeSuccessLog chargeSuccessLog = new ChargeSuccessLog();
        chargeSuccessLog.setGameName(inbox.getGameName());
        chargeSuccessLog.setMsisdn(inbox.getMsisdn());
        chargeSuccessLog.setKeyword(inbox.getKeyword());
        chargeSuccessLog.setOperator(inbox.getOperator());
        chargeSuccessLog.setSmsId(inbox.getId());
        chargeSuccessLog.setShortCode(inbox.getShortCode());
        chargeSuccessLog.setTransactionId(inbox.getTransactionId());
        inboxService.updateStatus(inbox, "S");
        return chargeSuccessLogRepository.save(chargeSuccessLog);
    }
}
