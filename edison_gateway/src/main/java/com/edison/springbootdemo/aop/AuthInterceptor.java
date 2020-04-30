package com.edison.springbootdemo.aop;

import com.edison.springbootdemo.constant.ResponseConstant;
import com.edison.springbootdemo.constant.SystemConstant;
import com.edison.springbootdemo.context.GlobalContext;
import com.edison.springbootdemo.domain.Response;
import com.edison.springbootdemo.domain.RspException;
import com.edison.springbootdemo.domain.UserCache;
import com.edison.springbootdemo.utils.SeqnoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**一个打印请求信息的拦截器*/
public class AuthInterceptor extends BaseInterceptor  {
    @Autowired
    SeqnoGenerator seqnoGenerator;

    @Resource(name = "normalRedisTemplate")
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //生成全局流水号
//        System.out.println("Interceptor: seqnoGenerator="+seqnoGenerator);
        GlobalContext.getContext().setReqId(seqnoGenerator.getGlobalReqId());
//        System.out.println("Interceptor-preHandle: 当前请求全局流水号："+GlobalContext.getContext().getReqId());

//        System.out.println("Interceptor-preHandle: 过滤器TestInterceptor 前处理:"+request.getRequestURI()+"?"+request.getQueryString());
//        System.out.println("Interceptor-preHandle: Content Length: " + request.getContentLength());
        //获取请求方式获取请求方式(GET与POST为主,也会有PUT、DELETE、INPUT)
//        System.out.println("Interceptor-preHandle: HTTP Method: " + request.getMethod());
//        System.out.println("Interceptor-preHandle: contextPath= " +request.getContextPath());
//        System.out.println("Interceptor-preHandle: 收到的请求："+request.getRequestURI()+"?"+request.getQueryString());
        Cookie[] cookies=request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                System.out.println("Interceptor-preHandle: Cookie信息：" + cookie.getName() + "=" + cookie.getValue());
            }
        }
        HttpSession session=request.getSession(false);
        if(session!=null){
            System.out.println("Interceptor-preHandle: session id="+session.getId());
        }

        //检查是否过于频繁请求
        boolean isFrequent=checkFrequency(request);
        if(isFrequent){
            //将错误信息返回
            BaseInterceptor.returnJson(response,new Response(ResponseConstant.REQUEST_TO_FREQUENT));
            return false;//直接拦截请求
        }

        boolean result=super.preHandle(request, response, handler);
        return result;//这里返回false的话，请求将不会到达controller，所以Interceptor一般用于权限验证等。
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("Interceptor-postHandle: 过滤器TestInterceptor 后处理"+request.getRequestURI()+"?"+request.getQueryString());
        super.postHandle(request, response, handler, modelAndView);
    }

    /**过于频繁访问的规则：
     * 同一个ip或用户在30秒内访问次数超过60次，则限制访问120秒*/
    public boolean checkFrequency(HttpServletRequest request) throws Exception{
        String key=request.getRemoteHost();

        HttpSession session=request.getSession(false);
        if(session!=null){//未登录 游客访问 通过ip限制
            Object userCache=session.getAttribute(SystemConstant.USERCACHE_KEY);
            if(userCache!=null){
                key=((UserCache)userCache).getUserId();
                System.out.println("当前用户："+key);
            }
        }
        key="auth_fr_"+key;
        long times=redisTemplate.opsForValue().increment(key);//访问次数加一
        if(times>10){//60比较合适 15用于测试
            redisTemplate.expire(key,120, TimeUnit.SECONDS);//这样被限制之后每次访问都会重新计时限制时间
            return true;
        }
        if(times==1){
            redisTemplate.expire(key,30, TimeUnit.SECONDS);
        }
        return false;
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
