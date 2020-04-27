package com.edison.springbootdemo.domain;

import com.edison.springbootdemo.constant.ResponseConstant;

public class RspException extends Exception {
    ResponseConstant responseCode;

    public RspException(ResponseConstant responseConstant){
        super(responseConstant.getMessage());
        this.responseCode=responseConstant;
    }

    ResponseConstant getResponseCode(){
        return this.responseCode;
    }

}
