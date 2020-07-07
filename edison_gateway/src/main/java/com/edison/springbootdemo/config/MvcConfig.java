package com.edison.springbootdemo.config;

import com.edison.springbootdemo.aop.EncryptFilter;
import com.edison.springbootdemo.aop.AuthInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

/**配置interceptor和Filter*/
@EnableRedisHttpSession(
        maxInactiveIntervalInSeconds = 20*60,//20分钟 session过期时间 tomcat会覆盖
        redisNamespace = "EDISON.SESSION",
        redisFlushMode = RedisFlushMode.IMMEDIATE //
)
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${swagger2.enable}")
    private boolean swagger;

    /**配置session存储序列化*/
    @Bean(name = "springSessionDefaultRedisSerializer")
    public RedisSerializer springSessionDefaultRedisSerializer() {
        return RedisSerializer.json();
    }

    /**uri忽略大小写，但是意义不大，毕竟页面都是前端传递过来的，都是约定好的，又不需要用户自己输入*/
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration=registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/**/error")
                ;
        if(swagger){
            interceptorRegistration.excludePathPatterns("/file/**",
                    "/csrf","/swagger**","/webjars/**","/api-docs**","/api-docs**/**","/swagger**/**");
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

    /**文件上传大小限制*/
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(25)); //25MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(100));//100M
        return factory.createMultipartConfig();
    }
}
