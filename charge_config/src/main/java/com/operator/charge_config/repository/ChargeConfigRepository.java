package com.operator.charge_config.repository;

import com.operator.charge_config.model.ChargeConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChargeConfigRepository extends JpaRepository<ChargeConfig, Long> {
    Optional<ChargeConfig> findByOperator(String operator);
}
