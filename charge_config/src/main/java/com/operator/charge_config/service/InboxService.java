package com.operator.charge_config.service;

import com.operator.charge_config.base.ApiResponse;
import com.operator.charge_config.model.Inbox;
import com.operator.charge_config.util.Contents;

public interface InboxService {

    Inbox save(Contents contents, String keyword, String gameName);
    Inbox findById(Long id);

    void updateStatus(Inbox inbox, String status);
}
