package com.operator.charge_config.service;

import com.operator.charge_config.model.ChargeConfig;

public interface ChargeConfigService {
    ChargeConfig findByOperator(String operator);
}
