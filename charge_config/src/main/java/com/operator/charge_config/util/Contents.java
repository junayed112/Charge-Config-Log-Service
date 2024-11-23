package com.operator.charge_config.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contents {

    private String transactionId;

    private String operator;

    private String shortCode;

    private String msisdn;

    private String sms;
}
