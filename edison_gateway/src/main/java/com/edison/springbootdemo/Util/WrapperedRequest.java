package com.edison.springbootdemo.Util;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**这个可以实现request的替换 包装？？
 * 请求体部分不用变，主要是对于请求体的getReader()和getInputStream()需要替换为从传入的requestBody进行读取。*/
public class WrapperedRequest extends HttpServletRequestWrapper {
    private String requestBody=null;
    HttpServletRequest request =null;

    public WrapperedRequest(HttpServletRequest req){
        super(req);
        this.request =req;
    }

    public WrapperedRequest(HttpServletRequest request,String requestBody){
        super(request);
        this.requestBody=requestBody;
        this.request =request;
    }



    /**请求体需要重载获取reader和InputStream方法*/
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new StringReader(requestBody));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            private InputStream in=new ByteArrayInputStream(
//                    requestBody.getBytes(request.getCharacterEncoding())
                    requestBody.getBytes("UTF-8")
            );
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return in.read();
            }
        };
    }
}
