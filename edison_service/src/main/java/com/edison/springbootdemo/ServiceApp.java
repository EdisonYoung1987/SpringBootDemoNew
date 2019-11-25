package com.edison.springbootdemo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

import javax.annotation.PostConstruct;

@MapperScan("com.edison.springbootdemo.mapper") //需要指定mapper地址，不然报错
@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class})
@EnableDubbo
public class ServiceApp {
    public static Logger logger= LoggerFactory.getLogger(ServiceApp.class);
    public static void main(String[] args) {
        System.out.println("ServiceApp starting...");
        SpringApplication.run(ServiceApp.class, args);
        System.out.println("ServiceApp started...");
    }
    @PostConstruct
    public void init(){
        logger.info("执行@postConstruct");
    }
}
