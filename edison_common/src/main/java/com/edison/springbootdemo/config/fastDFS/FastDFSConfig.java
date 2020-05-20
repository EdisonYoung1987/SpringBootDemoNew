package com.edison.springbootdemo.config.fastDFS;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Data
@ConfigurationProperties(prefix = "fastdfs") //一般和EnableConfigurationProperties搭配使用，否则需要使用@Component让spring进行bean加载和实例化
//@Component
/*@PropertySource(value = {"classpath:static/config/authorSetting.properties"},
       ignoreResourceNotFound = false, encoding = "UTF-8", name = "authorSetting.properties")*/
public class FastDFSConfig  {
    String connect_timeout_in_seconds;
    String network_timeout_in_seconds;
    String charset;
    String http_anti_steal_token;
    String http_secret_key;
    String http_tracker_http_port;
    String tracker_servers;


}
