package com.edison.springbootdemo.filters;

import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.filter.TpsLimitFilter;

public class MyTpsLimitFilter extends TpsLimitFilter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        if(invocation.getMethodName().matches("findAll")){//可以指定方法名进行控制
            return super.invoke(invoker,invocation);
        }

        return invoker.invoke(invocation);
    }
}
