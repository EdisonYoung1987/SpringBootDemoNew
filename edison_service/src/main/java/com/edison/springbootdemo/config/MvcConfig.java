package com.edison.springbootdemo.config;

import com.edison.springbootdemo.aop.EncryptFilter;
import com.edison.springbootdemo.aop.LogInfoInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**配置interceptor和Filter*/
@EnableRedisHttpSession(
        maxInactiveIntervalInSeconds = 60*20,//20分钟 session过期时间 tomcat会覆盖
        redisNamespace = "EDISON.SESSION",
        redisFlushMode = RedisFlushMode.IMMEDIATE //
)
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${swagger2.enable}")
    private boolean swagger;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration=registry.addInterceptor(new LogInfoInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/**/error");
        if(swagger){
            interceptorRegistration.excludePathPatterns("/csrf","/swagger**","/webjars/**","/api-docs**","/api-docs**/**","/swagger**/**");
        }
    }

    //通过对FilterRegistrationBean进行处理
    //添加一个对请求响应进行加解密的Filter
    @Bean
    public FilterRegistrationBean modifyParametersFilter(){
        //Filter不能设置排除的url，所以需要在filter内部实现
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(getEncryptFilterBean());//一个自定义的加解密并替换req rsp的类
        registration.addUrlPatterns("/*");
        registration.setName("EncryptFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public EncryptFilter getEncryptFilterBean(){
        return new EncryptFilter();
    }
}
