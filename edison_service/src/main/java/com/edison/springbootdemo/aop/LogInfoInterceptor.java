package com.edison.springbootdemo.aop;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**一个打印请求信息的拦截器*/
public class LogInfoInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("过滤器TestInterceptor 前处理:"+request.getRequestURI()+"?"+request.getQueryString());
        System.out.println("Content Length: " + request.getContentLength());
        //获取请求方式获取请求方式(GET与POST为主,也会有PUT、DELETE、INPUT)
        System.out.println("HTTP Method: " + request.getMethod());
        System.out.println("contextPath= " +request.getContextPath());
        System.out.println("收到的请求："+request.getRequestURI()+"?"+request.getQueryString());
        Cookie[] cookies=request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                System.out.println("Cookie信息：" + cookie.getName() + "=" + cookie.getValue());
            }
        }
        HttpSession session=request.getSession(false);
        if(session!=null){
            System.out.println("session id="+session.getId());
        }else{
            System.out.println("no session");
        }
        boolean result=super.preHandle(request, response, handler);
        return result;//这里返回false的话，请求将不会到达controller，所以Interceptor一般用于权限验证等。
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("过滤器TestInterceptor 后处理"+request.getRequestURI()+"?"+request.getQueryString());
        super.postHandle(request, response, handler, modelAndView);
    }

   /* @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }*/
}
