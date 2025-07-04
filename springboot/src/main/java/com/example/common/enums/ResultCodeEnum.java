package com.example.common.enums;

public enum ResultCodeEnum {
    SUCCESS("200", "Success"),

    PARAM_ERROR("400", "Parameter Error"),
    TOKEN_INVALID_ERROR("401", "Invalid Token"),
    TOKEN_CHECK_ERROR("401", "Token verification failed, please login again"),
    PARAM_LOST_ERROR("4001", "Missing Parameter"),

    SYSTEM_ERROR("500", "System Error"),
    USER_EXIST_ERROR("5001", "Username already exists"),
    USER_NOT_LOGIN("5002", "User not logged in"),
    USER_ACCOUNT_ERROR("5003", "Incorrect username or password"),
    USER_NOT_EXIST_ERROR("5004", "User does not exist"),
    PARAM_PASSWORD_ERROR("5005", "Incorrect original password"),
    ;

    public String code;
    public String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}