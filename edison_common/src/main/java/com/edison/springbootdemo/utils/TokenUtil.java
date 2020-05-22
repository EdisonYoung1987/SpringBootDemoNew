package com.edison.springbootdemo.utils;

import java.util.concurrent.ThreadLocalRandom;

public class TokenUtil {
    private static final char[] token_base={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F',
            'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0'};

    /**生成一个16位长度的字符串作为token*/
    public static String genToken16(){
        ThreadLocalRandom localRandom=ThreadLocalRandom.current();//线程内执行，不会多次创建 效率高于Random
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<16;i++) {
            sb.append(token_base[localRandom.nextInt(token_base.length)]);
        }
        return sb.toString();
    }
}
