package com.harry.mall.common;

public enum ResultCode implements ErrorCode {

    SUCCESS("001200", "operation succeeded"),
    FAILED("001400000", "operation failed"),
    GET_FAILED("001400001", "get failed"),
    CREATE_FAILED("001400002", "create failed"),
    UPDATE_FAILED("001400003", "update failed"),
    DELETE_FAILED("001400004", "delete failed"),
    VALIDATE_FAILED("001400005", "validation failed"),
    UNAUTHORIZED("001400006", "Not login or token expired"),
    FORBIDDEN("001400007", "No access");

    private String code;
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
