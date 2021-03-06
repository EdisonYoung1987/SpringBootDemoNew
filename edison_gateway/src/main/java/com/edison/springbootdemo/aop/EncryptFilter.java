package com.edison.springbootdemo.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edison.springbootdemo.Util.ServletUtil;
import com.edison.springbootdemo.Util.WrapperedRequest;
import com.edison.springbootdemo.Util.WrapperedResponse;
import com.edison.springbootdemo.constant.ResponseConstant;
import com.edison.springbootdemo.constant.SystemConstant;
import com.edison.springbootdemo.domain.Response;
import com.edison.springbootdemo.domain.RspException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**假装加解密*/
@Slf4j
public class EncryptFilter extends OncePerRequestFilter implements CommandLineRunner {
    @Value("${enableTest}")
    private String enableTest;

    private static Set<String> excludedUrlsSet=new HashSet<>(64);
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String uri=httpServletRequest.getRequestURI();
        log.info("请求路径:"+uri);
        Map<String, String[]> map=httpServletRequest.getParameterMap();
        Set<String> set=map.keySet();
        for(String key:set){
            log.info(map.get(key).toString());
        }

        /*//游客模式或登录会话已过期直接跳过 是否需要登录需要Controller中自己检查session以及session中的userCache是否为空
        if(httpServletRequest.getSession(false)==null && !"/login".equals(uri)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }--暂不支持游客模式*/

        log.info("EncryptFilter:执行EncryptFilter开始...");
        try {
            if(!isNeedDecrypt(httpServletRequest)){//测试环境不需要加密
                log.info("EncryptFilter:不需要解密");
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return;
            }
            log.info("EncryptFilter:需要解密");

            StringBuilder sb=ServletUtil.getRequestBody(httpServletRequest);
            String bodyToDecrypt=sb.toString();
//            log.info("EncryptFilter:解密前body="+bodyToDecrypt);

            //进行解密 比如登录时采用RSA,key为私钥，其他情况为AES,key为sessionId
            String bodyDecrypted=null;
            if("/login".equals(uri)) {
                bodyDecrypted = ServletUtil.decyptRsa(bodyToDecrypt, "xxxrr");//假装采用rsa-私钥进行解密
            }else{//其他请求使用用户登陆时前端传过来的key进行对称加解密
                HttpSession session=httpServletRequest.getSession(false);
                if(session==null || session.getAttribute(SystemConstant.AES_KEY)==null){
                    throw new RspException(ResponseConstant.LOGIN_NO_LOGIN);
                }
                String secret=(String)session.getAttribute(SystemConstant.AES_KEY); //获取aes加解密的key
                log.info("当前用户的密钥:{}",secret);
                bodyDecrypted= ServletUtil.decyptAes(bodyToDecrypt,secret);

                //将解密后的内容保存到session中，后面AuthInterceptor就不需要再读一次
                session.setAttribute(SystemConstant.BODY_KEY,bodyDecrypted);

                //将解密后的内容转为JsonObj保存在session中
                JSONObject jsonObject=JSONObject.parseObject(bodyDecrypted);
                session.setAttribute(SystemConstant.BODY_JSON_KEY,jsonObject);

                //TODO xss处理暂无

            }

            //将解密后的内容或不需要解密的报文体转换为JSON对象并保存到session中

            //用解密后的内容替换原始request
            WrapperedRequest requestWrapper=new WrapperedRequest(httpServletRequest,bodyDecrypted);
            WrapperedResponse responseWrapper=new WrapperedResponse(httpServletResponse);
            filterChain.doFilter(requestWrapper,responseWrapper);

            //处理返回内容，检查是否需要加密
//            log.info("EncryptFilter:这里开始进行响应处理");
            handleResponse(httpServletRequest,httpServletResponse,responseWrapper);
        } catch (Exception e) {//这里是不是就相当于拦截了所有的异常信息？？
            log.info("EncryptFilter拦截了异常！");
            e.printStackTrace();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            //具体操作
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(JSON.toJSONString(Response.error(e)));
            //
            writer.flush();
            writer.close();
        }
    }

    private void handleResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, WrapperedResponse responseWrapper) {
        String rspString="ERROR";
        if(isNeedDecrypt(httpServletRequest)){
            log.info("EncryptFilter:响应报文进行加密");
            try {//这里属于响应，不管是登录还是其他，都使用AES加密
                byte[] rspData=responseWrapper.getResponseData();
                rspString=ServletUtil.encyptAes(new String(rspData,"UTF-8"),"ENCRYPTED:");
                log.info("EncryptFilter:加密后的响应报文："+rspString);

                //这里需要重新设置contentlength，否则客户端接收信息不完整。
                //这里要注意 响应包含中文时，String.length()是不准确的
                httpServletResponse.setContentLength(rspString.getBytes("UTF-8").length);
                writeResponse(httpServletResponse,rspString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            log.info("EncryptFilter:响应报文不需要加密");
            writeResponse(httpServletResponse,responseWrapper);
        }

    }
    private void writeResponse(HttpServletResponse response,WrapperedResponse wrapperedResponse){
        try {
            OutputStream out=response.getOutputStream();
            out.write(wrapperedResponse.getResponseData());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeResponse(HttpServletResponse httpServletResponse, String rspString) {
        try {
            PrintWriter pw=httpServletResponse.getWriter();
            pw.print(rspString);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**是否需要解密*/
    private boolean isNeedDecrypt(HttpServletRequest request){
        if(!request.getMethod().equalsIgnoreCase("POST")
                && !request.getMethod().equalsIgnoreCase("GET")){
            return false;
        }
        //header中isTest为true且配置文件中enableTest为true
        String isTest=request.getHeader("isTest");
        if("true".equals(isTest)){
            if("true".equals(enableTest)){
                return false;
            }
        }
        if(excludedUrlsSet.contains(request.getRequestURI())){
            return false;
        }
        for(String url:excludedUrlsSet){
            if(request.getRequestURI().startsWith(url)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void run(String... args) throws Exception {
        //这里可以对该Filter做额外初始化，比如获取加解密用到的私钥和公钥
        //还可以获取本地配置的是否需要加解密的uri
        log.info("EncryptFilter:执行EncryptFilter初始化run()完成");
        excludedUrlsSet.add("/pk");//获取密钥的请求url不需要加解密
        excludedUrlsSet.add("/file");//文件上传也不用
        excludedUrlsSet.add("/swagger");
        excludedUrlsSet.add("/webjars");
        excludedUrlsSet.add("/favicon");
        excludedUrlsSet.add("/v2/api-docs");

    }
}
