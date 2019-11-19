package com.edison.springbootdemo.constant;

public enum ResponseConstant {
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