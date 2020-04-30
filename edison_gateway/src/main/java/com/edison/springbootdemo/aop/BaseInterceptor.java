package com.edison.springbootdemo.aop;

import com.alibaba.fastjson.JSON;
import com.edison.springbootdemo.constant.ResponseConstant;
import com.edison.springbootdemo.domain.Response;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Writer;

public class BaseInterceptor extends HandlerInterceptorAdapter {
    public static void returnJson(HttpServletResponse response, Response resp){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer=null;
        try {
            writer=response.getWriter();
            writer.write(JSON.toJSONString(resp));
        }catch (Exception e){
            System.err.println("返回信息错误:"+e.getMessage());
        }finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
