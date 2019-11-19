package com.edison.springbootdemo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 定制的的redis配置类！<br>
 * 提供三种redisTemplate，分别操作redis不同的数据库
 */

@ConditionalOnProperty(name="custom.redis.ennable",havingValue = "true") //通用还是定制化的redis
@Configuration
public class CustomRedisConfig {
    //TODO 还未完成自定义的 主要通过定义redis的连接工厂，然后重新定义redisTemplate


}
