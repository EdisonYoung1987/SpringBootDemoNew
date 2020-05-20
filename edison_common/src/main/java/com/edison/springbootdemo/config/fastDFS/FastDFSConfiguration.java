package com.edison.springbootdemo.config.fastDFS;

import lombok.extern.slf4j.Slf4j;
import org.csource.fastdfs.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.rmi.server.ExportException;
import java.util.Properties;

@Slf4j
//@ConditionalOnProperty(name = "fastdfs.ennable",havingValue = "true")
@EnableConfigurationProperties(FastDFSConfig.class)//该注解可以触发spring对FastDFSConfig进行实例化，否则该config类需要注解@Component
@Configuration
public class FastDFSConfiguration {
    @Bean("storageClient1")
    public StorageClient1 getStorageClient1(FastDFSConfig fastDFSConfig){
        try {
            log.info("初始化storageClient开始...");

            //加载fastdfs-client.properties配置文件 不能直接使用FastDFSConfig 因为属性名不能使用fastdfs.xxxx
            Properties properties = new Properties();
            properties.put("fastdfs.connect_timeout_in_seconds", fastDFSConfig.getConnect_timeout_in_seconds());
            properties.put("fastdfs.network_timeout_in_seconds", fastDFSConfig.getNetwork_timeout_in_seconds());
            properties.put("fastdfs.charset", fastDFSConfig.getCharset());
            properties.put("fastdfs.tracker_servers", fastDFSConfig.getTracker_servers());
            ClientGlobal.initByProperties(properties);
            // initFdfsConfig();
            //定义TrackerClient，用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();
            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Stroage
//            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            StorageServer storeStorage = null;
            //创建stroageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            log.info("初始化storageClient完成");
            return storageClient1;
        }catch (Exception e){
            log.error("初始化fastdfs客户端异常：",e);
        }
        return null;
    }

}
