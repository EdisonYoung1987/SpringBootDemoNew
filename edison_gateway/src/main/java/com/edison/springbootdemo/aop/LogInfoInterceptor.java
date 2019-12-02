package com.edison.springbootdemo.aop;

import com.edison.springbootdemo.context.GlobalContext;
import com.edison.springbootdemo.utils.SeqnoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**一个打印请求信息的拦截器*/
@Component
public class LogInfoInterceptor extends HandlerInterceptorAdapter {
    private SeqnoGenerator seqnoGenerator=new SeqnoGenerator();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //生成全局流水号
//        System.out.println("Interceptor: seqnoGenerator="+seqnoGenerator);
        GlobalContext.getContext().setReqId(seqnoGenerator.getGlobalReqId());
        System.out.println("Interceptor-preHandle: 当前请求全局流水号："+GlobalContext.getContext().getReqId());

        System.out.println("Interceptor-preHandle: 过滤器TestInterceptor 前处理:"+request.getRequestURI()+"?"+request.getQueryString());
        System.out.println("Interceptor-preHandle: Content Length: " + request.getContentLength());
        //获取请求方式获取请求方式(GET与POST为主,也会有PUT、DELETE、INPUT)
        System.out.println("Interceptor-preHandle: HTTP Method: " + request.getMethod());
        System.out.println("Interceptor-preHandle: contextPath= " +request.getContextPath());
        System.out.println("Interceptor-preHandle: 收到的请求："+request.getRequestURI()+"?"+request.getQueryString());
        Cookie[] cookies=request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                System.out.println("Interceptor-preHandle: Cookie信息：" + cookie.getName() + "=" + cookie.getValue());
            }
        }
        HttpSession session=request.getSession(false);
        if(session!=null){
            System.out.println("Interceptor-preHandle: session id="+session.getId());
        }else{
            System.out.println("Interceptor-preHandle: no session");
        }
        boolean result=super.preHandle(request, response, handler);
        return result;//这里返回false的话，请求将不会到达controller，所以Interceptor一般用于权限验证等。
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Interceptor-postHandle: 过滤器TestInterceptor 后处理"+request.getRequestURI()+"?"+request.getQueryString());
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
