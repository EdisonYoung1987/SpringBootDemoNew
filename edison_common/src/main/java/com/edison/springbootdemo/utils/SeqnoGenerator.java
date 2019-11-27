package com.edison.springbootdemo.utils;

import java.util.UUID;

/**序列化生成器*/
public class SeqnoGenerator {
    /**生成一个全局的id，用于跟踪
     * TODO 暂时用uuid替代，太长了，最好通过redis获取一批流水号，用完再取*/
    public static String getGlobalReqId(){
        return UUID.randomUUID().toString();
    }
}
