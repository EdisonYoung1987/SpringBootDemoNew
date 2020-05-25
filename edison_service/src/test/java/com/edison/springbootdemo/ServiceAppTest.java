package com.edison.springbootdemo;

import com.edison.springbootdemo.config.async.AsyncExecutor;
import com.edison.springbootdemo.service.MicroSvcsImpl.EmployeeMicrosvcs;
import com.edison.springbootdemo.utils.DistributeLocker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApp.class)
@Slf4j
public class ServiceAppTest {
    @Autowired
    EmployeeMicrosvcs employeeMicrosvcs;

    @Autowired
    DistributeLocker distributeLocker;

    @Autowired
    AsyncExecutor asyncExecutor;

    @Test
    public void test(){
        log.info("test start");
        employeeMicrosvcs.findAll();
        log.info("test end");

    }

    @Test
    public void testDistributeLock() throws Exception{
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                String name=Thread.currentThread().getName();
                log.info(name+"开始加锁");
                String uuid=distributeLocker.getLock("lockkey",10);
                log.info(name+"获取锁，进入sleep...");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("释放锁："+distributeLocker.releaseLock("lockkey",uuid));
            }
        };
        //将任务提交给异步线程池执行
        asyncExecutor.execute(runnable);
        asyncExecutor.execute(runnable);

        Thread.sleep(20000);
    }
}
