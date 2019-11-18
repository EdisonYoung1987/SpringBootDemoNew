package com.edison.springbootdemo.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${swagger2.enable}")
    private boolean enable;

    @Bean("用户模块")
    public Docket userApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/user.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("客户模块")
    public Docket customApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("客户模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/custom.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("雇员模块")
    public Docket emApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("雇员模块")
                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(ApiOperation.class))
//                .paths(PathSelectors.regex("/em_info.*"))
                .apis(RequestHandlerSelectors.basePackage("com.edison.springbootdemo.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("XXXXX系统平台接口文档")
                .description("提供子模块1/子模块2/子模块3的文档, 更多请关注公众号: 随行享阅")
                .termsOfServiceUrl("https://xingtian.github.io/trace.github.io/")
                .version("1.0")
                .build();
    }
}
