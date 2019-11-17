package com.edison.springbootdemo.aop;

import com.edison.springbootdemo.Util.ServletUtil;
import com.edison.springbootdemo.Util.WrapperedRequest;
import com.edison.springbootdemo.Util.WrapperedResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**假装加解密*/
public class EncryptFilter extends OncePerRequestFilter implements CommandLineRunner {
    private static Set<String> excludedUrlsSet=new HashSet<>(64);
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("执行EncryptFilter开始...");
        try {
            StringBuilder sb=ServletUtil.getRequestBody(httpServletRequest);
            String bodyToDecrypt=sb.toString();
            System.out.println("解密前body="+bodyToDecrypt);

            //进行解密 比如登录时采用RSA,key为私钥，其他情况为AES,key为sessionId
            String bodyDecrypted=bodyToDecrypt;
            if(isNeedDecrypt(httpServletRequest)){
                System.out.println("需要解密");
                bodyDecrypted=ServletUtil.stringDecypt(bodyToDecrypt,"xxxrr");
                System.out.println("解密后内容:"+bodyDecrypted);
            }else{
                System.out.println("不需要解密");
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return;
            }

            //用解密后的内容替换原始request
            WrapperedRequest requestWrapper=new WrapperedRequest(httpServletRequest,bodyDecrypted);
            WrapperedResponse responseWrapper=new WrapperedResponse(httpServletResponse);
            filterChain.doFilter(requestWrapper,responseWrapper);

            //处理返回内容，检查是否需要加密
            System.out.println("这里开始进行响应处理");
            handleResponse(httpServletRequest,httpServletResponse,responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("解密body失败");
        }
    }

    private void handleResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, WrapperedResponse responseWrapper) {
        String rspString="ERROR";
        if(isNeedDecrypt(httpServletRequest)){
            System.out.println("响应报文进行加密");
            try {
                byte[] rspData=responseWrapper.getResponseData();
                rspString=ServletUtil.stringEecypt(new String(rspData,"UTF-8"),"ENCRYPTED:");
                System.out.println("加密后的响应报文："+rspString);

                //这里需要重新设置contentlength，否则客户端接收信息不完整。
                httpServletResponse.setContentLength(rspString.length());
                writeResponse(httpServletResponse,rspString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("响应报文不需要加密");
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
        if(excludedUrlsSet.contains(request.getRequestURI())){
            return false;
        }
        return true;
    }

    @Override
    public void run(String... args) throws Exception {
        //这里可以对该Filter做额外初始化，比如获取加解密用到的私钥和公钥
        //还可以获取本地配置的是否需要加解密的uri
        System.out.println("执行EncryptFilter初始化...");
        excludedUrlsSet.add("xxx");
    }
}
