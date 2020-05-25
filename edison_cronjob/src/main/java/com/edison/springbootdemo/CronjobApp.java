package com.edison.springbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

import javax.annotation.PostConstruct;

@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class})
@Slf4j
public class CronjobApp {

    public static void main(String[] args) {
        log.info("SpringBootDemoApplication starting...");
        SpringApplication.run(CronjobApp.class, args);
        log.info("SpringBootDemoApplication started...");
    }
    @PostConstruct
    public void init(){
        log.info("执行InitOth.init()...");
    }
}
