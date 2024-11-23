package com.operator.charge_config.service;

import com.operator.charge_config.model.ChargeFailureLog;
import com.operator.charge_config.model.Inbox;

public interface ChargeFailureLogService {

    ChargeFailureLog save(Inbox inbox);
}
