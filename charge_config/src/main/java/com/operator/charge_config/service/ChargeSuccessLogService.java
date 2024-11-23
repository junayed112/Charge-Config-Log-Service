package com.operator.charge_config.service;

import com.operator.charge_config.model.ChargeSuccessLog;
import com.operator.charge_config.model.Inbox;

public interface ChargeSuccessLogService {
    ChargeSuccessLog save(Inbox inbox);
}
