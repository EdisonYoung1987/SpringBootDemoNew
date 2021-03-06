package com.edison.springbootdemo.service.MicroSvcsImpl;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.edison.springbootdemo.Imicrosvcs.I_EmployeeSvcs;
import com.edison.springbootdemo.config.async.AsyncExecutor;
import com.edison.springbootdemo.context.GlobalContext;
import com.edison.springbootdemo.domain.EmInfo;
import com.edison.springbootdemo.mapper.IEm_infoMapper;
import com.edison.springbootdemo.utils.ExceptionUtil;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.filter.TpsLimitFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**雇员微服务<p>*/
// 负载均衡策略可选：random, roundrobin, leastactive
@Service(loadbalance = "roundrobin",filter = {"Tps"},parameters = {"tps","100"})
@Component
public class EmployeeMicrosvcs implements I_EmployeeSvcs {
    Logger logger= LoggerFactory.getLogger(EmployeeMicrosvcs.class);
    @Autowired
    IEm_infoMapper iEm_infoMapper;
    @Autowired
    AsyncExecutor asyncExecutor;

    @Resource(name = "normalRedisTemplate")
    RedisTemplate<String,Object> redisTemplate;

    @Override
    @SentinelResource(value="findAll",entryType = EntryType.IN,
                 blockHandlerClass = ExceptionUtil.class,blockHandler = "handleException")
    public List<Map<String, Object>> findAll() {
        redisTemplate.opsForValue().set("service","123");
        logger.info("从网关通过dubbo传递过来的全局流水号：{}", GlobalContext.getContext().getReqId());
        logger.info("开始查询");
        List<Map<String,Object>> emInfos=iEm_infoMapper.findAll();
        for(Map<String,Object> emInfo: emInfos){
            Set<String> keys=emInfo.keySet();
            for(String key:keys){
                logger.info(key+"--"+emInfo.get(key));
            }
        }

        logger.info("执行异步任务");
        //execute方法被注解了@Async，所以execute方法实际上是将runnable提供给了@Async指定的线程池执行
        asyncExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //看全局流水号能否传递到线程池
//                logger.info(Thread.currentThread().getName() + ": 全局流水号：" + GlobalContext.getContext().getReqId());
            }
        });


        return  emInfos;
    }

    @Override
    @SentinelResource(value="lockOne",entryType= EntryType.IN)
    public void lockOne(String s) {

    }
}
