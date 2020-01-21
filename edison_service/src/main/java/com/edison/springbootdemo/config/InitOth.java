package com.edison.springbootdemo.config;

import com.edison.springbootdemo.ServiceApp;
import com.edison.springbootdemo.domain.EmInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
/**这几个InitOth是用来测试加载顺序的*/
@Configuration
public class InitOth implements CommandLineRunner {
    @Value("${spring.datasource.username}")
    private String username;

    public static Logger logger= LoggerFactory.getLogger(InitOth.class);

    @Bean("adf")
    EmInfo getEmInfo(){
        logger.info("执行getEmInfo() {}",username);
        return new EmInfo();
    }

    public InitOth(){
        logger.info("执行InitOth() {}",username);
    }

    @PostConstruct
    public void init(){
        logger.info("执行InitOth.@PostConstruct{}",username);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("执行InitOth.run{}",username);;
    }
}
