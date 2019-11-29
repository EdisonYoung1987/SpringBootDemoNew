package com.edison.springbootdemo.config.async;

import com.alibaba.ttl.TtlRunnable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**一个任务包装类*/
@Component
public class AsyncExecutor {
    @Async("asyncTaskExecutor") //指定异步任务执行的Executor，这个应该就是AsyncConfig里面配置的bean了
    public void execute(Runnable task){
        TtlRunnable.get(task).run(); //用TtlRunnable包装一下
//        task.run(); //如果不用ttl包装
    }
}
