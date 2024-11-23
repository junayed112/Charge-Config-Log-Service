package com.operator.charge_config.repository;

import com.operator.charge_config.model.ChargeFailureLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeFailureLogRepository extends JpaRepository<ChargeFailureLog, Long> {
}
