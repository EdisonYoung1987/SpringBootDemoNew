package com.edison.springbootdemo.utils;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.apache.dubbo.rpc.RpcException;

public class ExceptionUtil {
    public static void handleException(BlockException ex) {
        throw new RpcException("当前请求过多，请稍后重试");
    }
}
