package com.edison.springbootdemo.constant;

public enum ResponseConstant {
    REMOTE_SERVICE_UNAVAILABLE(888,"远程服务不可达"),
    SUCC_CODE(0,"成功");

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