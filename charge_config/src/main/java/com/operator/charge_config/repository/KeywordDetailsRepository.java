package com.operator.charge_config.repository;

import com.operator.charge_config.model.KeywordDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordDetailsRepository extends JpaRepository<KeywordDetails, Long> {
}
