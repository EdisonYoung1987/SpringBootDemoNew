package com.edison.springbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

import javax.annotation.PostConstruct;

@MapperScan("com.edison.springbootdemo.mapper") //需要指定mapper地址，不然报错
public class ServiceApp {

    public static void main(String[] args) {
        System.out.println("ServiceApp starting...");
        SpringApplication.run(ServiceApp.class, args);
        System.out.println("ServiceApp started...");
    }
    @PostConstruct
    public void init(){
        System.out.println("执行InitOth.init()...");
    }
}
