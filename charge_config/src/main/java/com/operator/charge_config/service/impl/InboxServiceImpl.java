package com.operator.charge_config.service.impl;

import com.operator.charge_config.base.ApiResponse;
import com.operator.charge_config.enumeration.InboxStatus;
import com.operator.charge_config.model.Inbox;
import com.operator.charge_config.repository.InboxRepository;
import com.operator.charge_config.service.InboxService;
import com.operator.charge_config.util.Contents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InboxServiceImpl implements InboxService {

    @Autowired
    private InboxRepository inboxRepository;

    @Override
    public Inbox save(Contents contents, String keyword, String gameName) {
        Inbox inbox = mapContentsToInbox(contents, keyword, gameName);
        return inboxRepository.save(inbox);
    }

    @Override
    public Inbox findById(Long id) {
        return inboxRepository.findById(id).get();
    }

    @Override
    public void updateStatus(Inbox inbox, String status) {
        inbox.setStatus(status);
        inboxRepository.save(inbox);
    }

    private Inbox mapContentsToInbox(Contents contents, String keyword, String gameName) {
        return Inbox.builder()
                .keyword(keyword)
                .gameName(gameName)
                .sms(contents.getSms())
                .msisdn(contents.getMsisdn())
                .status(InboxStatus.NEW.getCode())
                .shortCode(contents.getShortCode())
                .transactionId(contents.getTransactionId())
                .operator(contents.getOperator())
                .build();
    }
}