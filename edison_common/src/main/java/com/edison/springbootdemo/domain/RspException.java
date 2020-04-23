package com.edison.springbootdemo.domain;

import com.edison.springbootdemo.constant.ResponseConstant;

public class RspException extends Exception {
    public RspException(ResponseConstant response){
        super(response.getMessage());
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
