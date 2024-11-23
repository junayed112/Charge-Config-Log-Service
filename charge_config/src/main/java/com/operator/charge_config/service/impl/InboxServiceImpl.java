package com.operator.charge_config.service.impl;

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
        Inbox inbox = new Inbox();
        inbox.setKeyword(keyword);
        inbox.setGameName(gameName);
        inbox.setSms(contents.getSms());
        inbox.setMsisdn(inbox.getMsisdn());
        inbox.setStatus("N");
        inbox.setShortCode(contents.getShortCode());
        inbox.setTransactionId(contents.getTransactionId());
        inbox.setOperator(contents.getOperator());
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

}
