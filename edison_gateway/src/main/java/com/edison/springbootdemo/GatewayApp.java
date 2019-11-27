package com.edison.springbootdemo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class);
        System.out.println("Gateway 已启动");
    }
}
