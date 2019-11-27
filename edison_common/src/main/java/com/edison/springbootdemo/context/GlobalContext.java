package com.edison.springbootdemo.context;
/**一个全局的公共类，用来保存一些公共信息*/
public class GlobalContext {
    public static final String REQ_ID_KEY="reqId";

    //农商行使用的是alibaba.ttl.TransmittableThreadLocal
    //TODO 正常来说，这个GlobalContext应该是一个请求一个，而不是一个线程一个，肯定还是要改一下
    //InternalThreadLocal是什么，dubbo的RpcContext用的这个
    public static final ThreadLocal<GlobalContext> context=new ThreadLocal<GlobalContext>(){
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
