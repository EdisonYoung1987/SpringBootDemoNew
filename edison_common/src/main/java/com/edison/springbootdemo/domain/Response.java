package com.edison.springbootdemo.domain;

import com.edison.springbootdemo.constant.ResponseConstant;

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

    //构造错误信息
    public static Response error(int errCd,String message){
        Response response=new Response();
        response.setStatusCode(errCd);
        response.setRetMessage(message);
        return response;
    }

    //构造异常信息
    public static Response error(Exception e){
        Response response=new Response();
        if(e instanceof RspException) {
            ResponseConstant code=((RspException) e).getResponseCode();
            response.setStatusCode(code.getCode());
            response.setRetMessage(code.getMessage());
        }else{
            response.setStatusCode(ResponseConstant.SYSTEM_ERR_CODE.getCode());
            response.setRetMessage(ResponseConstant.SYSTEM_ERR_CODE.getMessage() + ":" + e.getMessage());
        }
        return response;
    }
}
