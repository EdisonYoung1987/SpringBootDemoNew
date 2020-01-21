package com.edison.springbootdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitOth3 implements CommandLineRunner {
    public static Logger logger= LoggerFactory.getLogger(InitOth3.class);

    public InitOth3(){
        logger.info("执行InitOth3()");
    }

    @PostConstruct
    public void init(){
        logger.info("执行InitOth3.@PostConstruct");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("执行InitOth3.run");
    }
}
