package com.edison.springbootdemo.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class InitOth {
    @PostConstruct
    public void init(){
        System.out.println("执行InitOth.init()0...");
    }
}
