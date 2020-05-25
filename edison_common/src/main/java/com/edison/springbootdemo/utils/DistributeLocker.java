package com.edison.springbootdemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**基于redis实现的分布式锁。<p>
 * CQRCB采用的是AOP注解方式对方法加锁，
 * 个人感觉这样做虽然更方便，当是锁粒度过大-容易超时，除非将要加锁的片段拆分出去-会形成太多代码碎片</>*/
@Component
@Slf4j
public class DistributeLocker {
    //等待锁的sleep时间
    private static final long WAIT_LOCK_TIME=10L;
    @Resource(name = "normalRedisTemplate")
    RedisTemplate<String,Object> redisTemplate;

    //存在key则删除返回1，否则返回0，用于解锁
    private static final RedisScript<Long> releaseScript=RedisScript.of("if redis.call('get',KEYS[1]) == ARGV[1] then return" +
            " redis.call('del',KEYS[1]) else return 0 end",Long.class);

    /**尝试加锁--不支持重入：<p>
     * @param lockKey 加锁key
     * @param lockSeconds 加锁时间：秒-考虑网络传输、业务处理耗时，最低单位秒
     * 成功：返回uuid作为解锁依据<br>
     * 失败：返回null*/
    public String tryLock(String lockKey,long lockSeconds){
        log.info(Thread.currentThread().getName()+"trylock...");
        String uuid= UUID.randomUUID().toString();

        //存在则设置key
        Boolean res=redisTemplate.opsForValue().setIfAbsent(lockKey,uuid,lockSeconds, TimeUnit.SECONDS);
        if(res!=null && res==true) {
            return uuid;
        }else {
            return null;
        }
    }

    /**加锁--不支持重入：<p>
     * @param lockKey 加锁key
     * @param lockSeconds 加锁时间：秒-考虑网络传输、业务处理耗时，最低单位秒
     * 成功：返回uuid作为解锁依据<br>
     * 失败：返回null*/
    public String getLock(String lockKey, long lockSeconds) {
        log.info(Thread.currentThread().getName()+"getlock...");
        String uuid;
        while(null==(uuid=tryLock(lockKey,lockSeconds))) {
            long expireTime=redisTemplate.getExpire(lockKey,TimeUnit.SECONDS);
            log.info("当前lock ttl还有："+expireTime);
            try {
                Thread.sleep(WAIT_LOCK_TIME); //不能以锁存活时间为sleep时间，因为获得锁的应用基本上都会很快释放锁。
            } catch (InterruptedException e) { }
        }
        if(Thread.currentThread().isInterrupted()) {//说明等待锁期间被interrupt过，之前中断信号被捕获，重新发起一次
            Thread.currentThread().interrupt();
        }
        return uuid;
    }
    public String getLcokInterruptly(String lockKey, long lockSeconds,long waitSeconds) throws InterruptedException{
        String uuid=null;
        while(null==(uuid=tryLock(lockKey,lockSeconds))) {
            long expireTime=redisTemplate.getExpire(lockKey,TimeUnit.SECONDS);
             Thread.sleep(expireTime*1000);
        }
        return uuid;
    }

    /**解锁：用加锁返回的字串作为解锁依据
     * @return  true - 成功 false - 失败*/
    public boolean releaseLock(String lockKey,String uuid){
        log.info(Thread.currentThread().getName()+"releaselock...");

        long ret=redisTemplate.execute(releaseScript, Collections.singletonList(lockKey),uuid);
        return ret==1;
    }
}
