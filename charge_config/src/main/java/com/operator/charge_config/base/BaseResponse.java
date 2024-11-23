package com.operator.charge_config.base;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private Integer statusCode;
    private String message;
    private Long contentCount;
    private T contents;
}
