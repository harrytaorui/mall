package com.harry.mall.common;

public enum ResultCode implements ErrorCode {

    SUCCESS(200, "operation succeeded"),
    FAILED(400, "operation failed"),
    VALIDATE_FAILED(404, "parameters validation failed"),
    UNAUTHORIZED(401, "Not login or token expired"),
    FORBIDDEN(403, "No access");

    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
