package com.edison.springbootdemo.filters;

import com.edison.springbootdemo.context.GlobalContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**此处扩展filter主要是为了将网关系统调用微服务时传递一个全场唯一流水号，当然也可以将其他信息传递过去<p>
 * @Activate// 无条件自动激活
 * @Activate(“xxx”)// 当配置了xxx参数，并且参数为有效值时激活，比如配了
 * @Activate(group =“provider”, value =“xxx”) 只对提供方激活，group可选"provider"或"consumer"
 * </>*/

@Activate(group = {"provider","consumer"},order = 0) //表示provider和consumer都要调用该扩展
//@Activate //无条件激活
@Slf4j
public class DeliverDubboFilter implements Filter {
    private static final Logger logger= LoggerFactory.getLogger(DeliverDubboFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        log.info("dubbo的DeliverDubboFilter被调用");
        String dubboReqId=RpcContext.getContext().getAttachment(GlobalContext.REQ_ID_KEY);
        String globalReqId=GlobalContext.getContext().getReqId();//获取当前请求的全局id，在web的filter应该设置

        if(StringUtils.isEmpty(dubboReqId) && StringUtils.isEmpty(globalReqId)){//两者皆为空
            logger.error("全局id未生成");
        }else if(StringUtils.isEmpty(dubboReqId)){//说明是消费者调用前执行的filter
            RpcContext.getContext().setAttachment(GlobalContext.REQ_ID_KEY,globalReqId);
        }else{//说明是provider在执行filter
            GlobalContext.getContext().setReqId(dubboReqId);
        }
        return invoker.invoke(invocation);
    }
}
