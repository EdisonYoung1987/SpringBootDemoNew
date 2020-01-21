package com.edison.springbootdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitOth1 implements CommandLineRunner {
    public static Logger logger= LoggerFactory.getLogger(InitOth1.class);

    public InitOth1(){
        logger.info("执行InitOth1()");
    }

    @PostConstruct
    public void init(){
        logger.info("执行InitOth1.@PostConstruct");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("执行InitOth1.run");
    }
}
