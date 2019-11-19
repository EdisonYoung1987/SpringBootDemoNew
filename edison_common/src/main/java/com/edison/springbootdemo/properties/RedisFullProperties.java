package com.edison.springbootdemo.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**为了定制化Redis，需要对Redis增加额外的自定义配置*/
@ConfigurationProperties(prefix = "spring.redis") //指定配置参数的前缀
@ConditionalOnProperty(name="custom.redis.ennable",havingValue = "false")
@Component
@Primary
public class RedisFullProperties extends RedisProperties {
    /**database-shared指定了该redisTemplate操作的redis数据库index*/
    private int databaseShared;

    /**database-data指定了该redisTemplate操作的redis数据库index*/
    private int databaseData;
    /**database-cache指定了该redisTemplate操作的redis数据库index*/
    private int databaseCache;

    public int getDatabaseShared() {
        return databaseShared;
    }

    public void setDatabaseShared(int databaseShared) {
        this.databaseShared = databaseShared;
    }

    public int getDatabaseData() {
        return databaseData;
    }

    public void setDatabaseData(int databaseData) {
        this.databaseData = databaseData;
    }

    public int getDatabaseCache() {
        return databaseCache;
    }

    public void setDatabaseCache(int databaseCache) {
        this.databaseCache = databaseCache;
    }
}
