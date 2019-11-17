package com.edison.springbootdemo.tasks;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@DisallowConcurrentExecution //禁止并发
public class CronTask extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("任务执行了:" + sdf.format(date)+"-"+Thread.currentThread().getName());
        try{
            Thread.sleep(3000+new Random(System.currentTimeMillis()).nextInt(7)*1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        date=new Date();
        System.out.println("任务完成了:" + sdf.format(date)+"-"+Thread.currentThread().getName());

        // indexController.testMail();
    }
}
