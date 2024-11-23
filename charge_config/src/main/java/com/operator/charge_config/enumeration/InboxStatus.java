package com.operator.charge_config.enumeration;

public enum InboxStatus {
    NEW("N"),
    SUCCESS("S"),
    FAILED("F");

    private final String code;

    InboxStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
