package com.operator.charge_config.model;

import com.operator.charge_config.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inbox extends BaseEntity {

    @Column(name = "sms", nullable = false)
    private String sms;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "operator", nullable = false)
    private String operator;

    @Column(name = "short_code", nullable = false)
    private String shortCode;

    @Column(name = "msisdn", nullable = false, length = 15)
    private String msisdn;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "status")
    private String status;
}
