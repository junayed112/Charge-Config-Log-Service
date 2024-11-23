package com.operator.charge_config.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnlockCodeRequestDto {

    private String transactionId;

    private String operator;

    private String shortCode;

    private String msisdn;

    private String keyword;

    private String gameName;
}
