package com.edison.springbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

import javax.annotation.PostConstruct;

@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class})
public class CronjobApp {

    public static void main(String[] args) {
        System.out.println("SpringBootDemoApplication starting...");
        SpringApplication.run(CronjobApp.class, args);
        System.out.println("SpringBootDemoApplication started...");
    }
    @PostConstruct
    public void init(){
        System.out.println("执行InitOth.init()...");
    }
}
