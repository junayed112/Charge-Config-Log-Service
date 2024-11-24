package com.operator.charge_config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends Throwable {
    private Integer statusCode;
    private String message;
}
