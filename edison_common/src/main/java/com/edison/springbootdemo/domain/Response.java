package com.edison.springbootdemo.domain;

import java.io.Serializable;

/**作为前后端交互的响应消息*/
public class Response implements Serializable {
    private int statusCode;  //返回码
    private String retMessage;  //返回消息
    private Object data;        //数据

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
