package com.operator.charge_config.repository;

import com.operator.charge_config.model.ChargeConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeConfigRepository extends JpaRepository<ChargeConfig, Long> {
}
