package com.edison.springbootdemo.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;

/**Servlet工具类*/
public class ServletUtil {
    /**获取请求体*/
    public static StringBuilder getRequestBody(HttpServletRequest request){
        StringBuilder sb=new StringBuilder(128);
        try{
            BufferedReader reader=request.getReader();
            char[] buffer=new char[10240];
            int count=0;
            while((count=reader.read(buffer))>=0){
                sb.append(buffer,0,count);
            }
            return sb;
        }catch(Exception e){
            e.printStackTrace();
        }
        return new StringBuilder(0);
    }
    /**获取请求体*/
    public static StringBuilder getRequestBody2(HttpServletRequest request){
        StringBuilder sb=new StringBuilder(128);
        try{
            InputStream is=request.getInputStream();
            byte[] buffer=new byte[1024];
            int count=0;
            while((count=is.read(buffer))>=0){
                sb.append(new String(buffer,0,count,"utf-8"));
            }
            return sb;
        }catch(Exception e){
            e.printStackTrace();
        }
        return new StringBuilder(0);
    }

    /**假装对请求体进行解密*/
    public static String stringDecypt(String stringToDecrypt,String key){
//        return key+":"+stringToDecrypt; //为保证json解析，不添加key了
        return stringToDecrypt.replaceFirst("2","3");
    }

    /**假装对请求体进行加密*/
    public static String stringEecypt(String stringToDecrypt,String key){
        return key+stringToDecrypt;
    }

    /**将String格式的json字符串转为javabean简单对象*/
    //可以参考https://www.cnblogs.com/wdzhz/p/11065571.html
    public static <T> T parseObject(String jsonStr,Class<T> clazz){
        return JSON.parseObject(jsonStr,clazz);
    }

    /**将一个json字符串转换成一个JSONObject；
     * 可以通过JSONObject.getString/getInteger/getBigdecimal("key")等等方式获取key对应的value*/
    public static JSONObject parseJSONObject(String jsonStr){
        return JSON.parseObject(jsonStr);
    }

    /**讲一个对象转为json字符串*/
    public static String getJSONString(Object obj){
        return JSON.toJSONString(obj);
    }
}
