package com.edison.springbootdemo.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**一个全局的公共类，用来保存一些公共信息*/
public class GlobalContext {
    public static final String REQ_ID_KEY="reqId";

    //ThreadLocal不能在线程之间传递
    //InheritableThreadLocal可继承在父子线程之间传递，但不使用线程池
    // alibaba.ttl.TransmittableThreadLocal可用于线程池传递信息，像此处的GlobalContext如果想要在
    //异步任务或者线程池中传递，需要用TransmittableTheadLocal，不过线程池需要用
    //InternalThreadLocal是什么，dubbo的RpcContext用的这个
//    public static final ThreadLocal<GlobalContext> context=new ThreadLocal<GlobalContext>(){
    public static final TransmittableThreadLocal<GlobalContext> context=new TransmittableThreadLocal<GlobalContext>(){
        @Override
        protected GlobalContext initialValue() {
            return new GlobalContext();
        }
    };

    private String reqId;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public static GlobalContext getContext(){
        return context.get();
    }
}
