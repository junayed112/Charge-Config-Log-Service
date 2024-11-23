package com.operator.charge_config.service.impl;

import com.operator.charge_config.model.ChargeConfig;
import com.operator.charge_config.repository.ChargeConfigRepository;
import com.operator.charge_config.service.ChargeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargeConfigServiceImpl implements ChargeConfigService {

    @Autowired
    private ChargeConfigRepository chargeConfigRepository;

    @Override
    public ChargeConfig findByOperator(String operator) {
        return chargeConfigRepository.findByOperator(operator).get();
    }
}
