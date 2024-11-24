package com.operator.charge_config.model;

import com.operator.charge_config.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChargeFailureLog extends BaseEntity {

    @Column(name = "sms_id", nullable = false)
    private Long smsId;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "operator", nullable = false)
    private String operator;

    @Column(name = "short_code", nullable = false)
    private String shortCode;

    @Column(name = "msisdn", nullable = false)
    private String msisdn;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "status_code")
    private int statusCode;

    @Column(name = "message")
    private String message;
}
