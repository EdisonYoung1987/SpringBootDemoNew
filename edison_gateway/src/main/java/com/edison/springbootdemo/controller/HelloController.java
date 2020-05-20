package com.edison.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class HelloController {
    @GetMapping("/hello" )
    public String hello(HttpServletRequest request,HttpServletResponse response){
        System.out.println(request.getCharacterEncoding());
        //请求使用的协议和版本
        System.out.println("Protocol: " + request.getProtocol());
        //返回当前所使用的协议：http  ftp等
        System.out.println("Scheme: " + request.getScheme());
        //请求被发送到的服务器主机名
        System.out.println("Server Name: " + request.getServerName());
        //请求被发送到的服务器端口
        System.out.println("Server Port: " + request.getServerPort());
        //客户端的IP地址
        System.out.println("Remote Addr: " + request.getRemoteAddr());
        //客户端的主机名
        System.out.println("Remote Host: " + request.getRemoteHost());
        //请求的字符编码
        System.out.println("Character Encoding: " + request.getCharacterEncoding());
        //请求的消息体（body）的大小 字节数，没有消息体的，返回-1
        System.out.println("Content Length: " + request.getContentLength());
        //返回请求的消息体的MIME类型，MIME是描述消息内容类型的因特网标准
        System.out.println("Content Type: " + request.getContentType());
        //获取保护servlet的认证方案名(BASIC或SSL 等),未受保护的servlet返回的就是null
        System.out.println("Auth Type: " + request.getAuthType());
        //获取请求方式获取请求方式(GET与POST为主,也会有PUT、DELETE、INPUT)
        System.out.println("HTTP Method: " + request.getMethod());
        //解释在下面
        System.out.println("Path Info: " + request.getPathInfo());
        //返回URL中的额外路径信息所对应的资源的真实路径。
        System.out.println("Path Trans: " + request.getPathTranslated());
        //获取url中参数的部分
        System.out.println("Query String: " + request.getQueryString());
        //如果客户登录了，那么获取用户的登录信息
        System.out.println("Remote User: " + request.getRemoteUser());
        //获取sessionID
        System.out.println("Session Id: " + request.getRequestedSessionId());
        //获取完整的url，不带参数的
        System.out.println("Request URI: " + request.getRequestURI());
        //获取请求路径
        System.out.println("Servlet Path: " + request.getServletPath());

        //接下来是获取消息头中的信息
        //获取accept等等
        System.out.println("Accept: " + request.getHeader("Accept"));
        System.out.println("Host: " + request.getHeader("Host"));
        System.out.println("Referer : " + request.getHeader("Referer"));
        System.out.println("Accept-Language : " + request.getHeader("Accept-Language"));
        System.out.println("Accept-Encoding : " + request.getHeader("Accept-Encoding"));
        System.out.println("User-Agent : " + request.getHeader("User-Agent"));
        System.out.println("Connection : " + request.getHeader("Connection"));
        System.out.println("Cookie : " + request.getHeader("Cookie"));
        System.out.println("Created : " + request.getSession().getCreationTime());
        System.out.println("LastAccessed : " + request.getSession().getLastAccessedTime());
        try {
            PrintWriter writer= response.getWriter();
            writer.write("QSSSS");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "HELLO";
    }
}
