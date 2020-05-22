package com.edison.springbootdemo;

import com.edison.springbootdemo.constant.ResponseConstant;
import com.edison.springbootdemo.domain.RspException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=GatewayApp.class)
public class TestApp {
    //不存在则设置
    private static final RedisScript<Long> releaseScript=
            RedisScript.of("if redis.call('EXISTS',KEYS[1]) == 0 then " +
                    " redis.call('SET',KEYS[1],ARGV[1],'EX',ARGV[2]) else return 1 end",Long.class);
    @Resource(name = "normalRedisTemplate")
    RedisTemplate<String,Object> redisTemplate;

    @Test
    public  void testDuplicate(){
        String key="TESTTTT";
        Long ret=redisTemplate.execute(releaseScript, Collections.singletonList(key),1,150);
        System.out.println(ret);

    }
}
