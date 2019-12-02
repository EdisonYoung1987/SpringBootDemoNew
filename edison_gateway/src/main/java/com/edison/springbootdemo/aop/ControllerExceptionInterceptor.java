package com.edison.springbootdemo.aop;

import com.edison.springbootdemo.domain.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**针对所有controller的异常拦截*/
@ControllerAdvice
public class ControllerExceptionInterceptor  {
    @ExceptionHandler(value = Exception.class)
    public Response errorHandler(Exception ex) {
        System.out.println("controller异常被拦截");
        return Response.error(ex);
    }
}
