package com.operator.charge_config.dto.response;

import lombok.Data;

@Data
public class UnlockCodeResponse {
    private Integer statusCode;
    private String message;
    private String unlockCode;
    private String transactionId;
    private String operator;
    private String shortCode;
    private String msisdn;
    private String keyword;
    private String gameName;
}
