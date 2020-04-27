package com.edison.springbootdemo.constant;

public enum ResponseConstant {
    SUCC_CODE(0,"成功"),

    LOGIN_WRONG_PARAMETERS(10001,"登录参数错误!"),
    REMOTE_SERVICE_UNAVAILABLE(888,"远程服务不可达"),
    SYSTEM_ERR_CODE(9999,"系统错误");

    int code;
    String message;
    ResponseConstant(int code, String msg) {
        this.code=code;
        this.message=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}