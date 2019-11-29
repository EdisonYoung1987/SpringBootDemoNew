package com.edison.springbootdemo.config.async;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@EnableAsync //开启异步
@Configuration
public class AsyncConfig {
    @Bean
    public Executor asyncTaskExecutor(){
        ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(200); //指定队列容量会定义LinkedBlockingQueue,否则定义SynchronousQueue不存储任务的阻塞队列
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setKeepAliveSeconds(60*10);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60*5);
        taskExecutor.setThreadNamePrefix("AsyncThread:");
        taskExecutor.initialize(); //初始化 线程池不存在启动这一说法，因为最开始都是提交一个任务启动一个线程，然后这个线程循环去取任务执行

        return TtlExecutors.getTtlExecutor(taskExecutor); //用ali的ttlExecutors进行包装
    }
}
