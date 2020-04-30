package com.edison.springbootdemo;

import org.apache.catalina.core.ApplicationContext;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
public class GatewayApp  {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx= SpringApplication.run(GatewayApp.class);
        /*//打印所有bean
        String beans[]=ctx.getBeanDefinitionNames();

        for(String bean:beans){
            if(bean.matches("normalRedisTemplate")){
                System.out.println("存在["+bean+"]");

            }else if(bean.matches("authInterceptor")){
                System.out.println("存在["+bean+"]");
            }

        }*/

        System.out.println("Gateway 已启动");
    }
}
