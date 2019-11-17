package com.edison.springbootdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:Quartz.properties")
public class QuartzPropertiesConfig {
    @Value("${Cronjob}") //因为加载顺序问题，这里无法注入
    String cronjobStr;

    public String cronjobStr(){
        return  this.cronjobStr;
    }
}
