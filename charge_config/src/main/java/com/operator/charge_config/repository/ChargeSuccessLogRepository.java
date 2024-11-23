package com.operator.charge_config.repository;

import com.operator.charge_config.model.ChargeSuccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeSuccessLogRepository extends JpaRepository<ChargeSuccessLog, Long> {
}
