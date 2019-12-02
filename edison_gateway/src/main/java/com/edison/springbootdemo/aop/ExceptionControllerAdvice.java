package com.edison.springbootdemo.aop;

import com.alibaba.fastjson.JSON;
import com.edison.springbootdemo.domain.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**针对所有controller的异常拦截*/
@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(HttpServletResponse response, Exception e,Exception ex) throws  Exception {
        System.out.println("ControllerAdvice:controller异常被拦截");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        //具体操作
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(Response.error(e)));
        //
        writer.flush();
        writer.close();
        return null;
    }
}
