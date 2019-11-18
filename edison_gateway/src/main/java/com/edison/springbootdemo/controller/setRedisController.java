package com.edison.springbootdemo.controller;

import com.edison.springbootdemo.property.RedisFullProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**这个controller用来测试多个redis连接的存储*/
@RestController
public class setRedisController {

    @Resource(name = "normalRedisTemplate") //考虑到定制化后又多个RedisTemplate实现类对象，每个都制定名称
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    RedisFullProperties redisFullProperties;

    @RequestMapping(value = "/setRedis",method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response){
        //先测试application.properties里面配置的额外redis信息能否被加载
        System.out.println("cache:"+redisFullProperties.getDatabaseCache());
        System.out.println("data:"+redisFullProperties.getDatabaseData());
        System.out.println("shared:"+redisFullProperties.getDatabaseShared());

        return "SETTED";
    }
}
