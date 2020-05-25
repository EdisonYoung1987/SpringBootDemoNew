package com.edison.springbootdemo.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edison.springbootdemo.constant.ResponseConstant;
import com.edison.springbootdemo.constant.SystemConstant;
import com.edison.springbootdemo.context.GlobalContext;
import com.edison.springbootdemo.domain.Response;
import com.edison.springbootdemo.domain.RspException;
import com.edison.springbootdemo.domain.UserCache;
import com.edison.springbootdemo.utils.SeqnoGenerator;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**一个打印请求信息的拦截器*/
@Slf4j
public class AuthInterceptor extends BaseInterceptor  {
    @Autowired
    SeqnoGenerator seqnoGenerator;

    @Resource(name = "normalRedisTemplate")
    RedisTemplate<String,Object> redisTemplate;

    //不存在则设置
    private static final RedisScript<Long> releaseScript=
             RedisScript.of("if redis.call('EXISTS',KEYS[1]) == 0 then " +
            " redis.call('SET',KEYS[1],ARGV[1],'EX',ARGV[2]) else return 1 end",Long.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //生成全局流水号
        String seqNo=seqnoGenerator.getGlobalReqId();
        GlobalContext.getContext().setReqId(seqNo);
        log.info("当前请求全局流水号:{}",seqNo);


        HttpSession session=request.getSession(false);
        if(session!=null){//已登陆
            log.info("Interceptor-preHandle: session id="+session.getId());

            //1. 验证签名
            //前端登陆时会在报文体中收到token字串，该字串需要参与签名，但后面通信不再进行传递
            //前端签名规则：MD5(解密后的报文体去掉_signature部分后按key字母排序后+token)
            JSONObject jsonObject=(JSONObject)session.getAttribute(SystemConstant.BODY_JSON_KEY);
            String token=(String) session.getAttribute(SystemConstant.TOKEN_KEY);
            log.info("拦截其中token={}",token);

            String signature=jsonObject.getString("_signature");
            jsonObject.remove("_signature");

            //TODO json内容排序后+token，计算md5值，然后和签名进行比较，不等则直接返回false

            //2. 检查请求是否超时 前端报文体必须附带_ts时间戳
            long ts=jsonObject.getLongValue("_ts"); //时间戳是unixtime类型的long型
            long curr=System.currentTimeMillis();
            if(Math.abs(curr-ts)>120*1000){//该请求超时
                throw  new RspException(ResponseConstant.REQUEST_TIMEOUT);
            }

            //3. 检查是否重复请求 userId+signature
            UserCache userCache=(UserCache)session.getAttribute(SystemConstant.USERCACHE_KEY);
            String userId=userCache.getUserId();
            String key=userId+signature;

            Long ret=redisTemplate.execute(releaseScript, Collections.singletonList(key),1,150);
            if(ret!=null && ret==1){
                throw new RspException(ResponseConstant.REQUEST_DUPLICATED);
            }

            //将session保存到GlobalContext中
            GlobalContext.getContext().setSession

        }else{//未登录 游客模式
            log.info("当前为游客模式 暂不支持");
            return false;//暂不支持
        }

        //4. 检查是否过于频繁请求
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
//        log.info("Interceptor-postHandle: 过滤器TestInterceptor 后处理"+request.getRequestURI()+"?"+request.getQueryString());
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
                log.info("当前用户："+key);
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
