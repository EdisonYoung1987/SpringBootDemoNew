package com.edison.springbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
@Slf4j
public class GatewayApp  {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx= SpringApplication.run(GatewayApp.class);
        /*//打印所有bean
        String beans[]=ctx.getBeanDefinitionNames();

        for(String bean:beans){
            if(bean.matches("normalRedisTemplate")){
                log.info("存在["+bean+"]");

            }else if(bean.matches("authInterceptor")){
                log.info("存在["+bean+"]");
            }

        }*/

        log.info("Gateway 已启动");
    }
}
