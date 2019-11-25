package com.edison.springbootdemo.config;

import com.edison.springbootdemo.ServiceApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class InitOth {
    public static Logger logger= LoggerFactory.getLogger(InitOth.class);

    @PostConstruct
    public void init(){
        logger.info("执行InitOth.@PostConstruct");
    }
}
