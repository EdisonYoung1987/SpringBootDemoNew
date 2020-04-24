package com.edison.springbootdemo.config;

import com.edison.springbootdemo.tasks.CronTask;
import com.edison.springbootdemo.tasks.SimpleTask;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Configuration
@PropertySource("classpath:Quartz.properties")
public class QuartzConfigure implements CommandLineRunner {
    @Resource
    SchedulerFactoryBean schedulerFactoryBean;

    @Autowired(required = false)
    private List<Trigger> triggers ;

    @Value("${Cronjob}") //因为加载顺序问题，这里无法注入
    String cronjobStr;

    @Value("${simpletask.interval}")
    String interval;

    // 使用jobDetail包装job
    @Bean
    public JobDetail myJobDetail() {
        return JobBuilder.newJob(SimpleTask.class).withIdentity("simpleTask").storeDurably().build();
    }

    // 把jobDetail注册到trigger上去
    @Bean
    public Trigger myJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        scheduleBuilder.withIntervalInSeconds(Integer.parseInt(interval));
//        scheduleBuilder.withRepeatCount(3);
        scheduleBuilder.repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(myJobDetail())
                .withIdentity("myJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    // 使用jobDetail包装job
    @Bean
    public JobDetail myCronJobDetail() {
        return JobBuilder.newJob(CronTask.class).withIdentity("CronTask").storeDurably().build();
    }

    // 把jobDetail注册到Cron表达式的trigger上去
    @Bean
    public Trigger CronJobTrigger() {//这里进行注入
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartzPropertiesConfig.cronjobStr());
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronjobStr);
        return TriggerBuilder.newTrigger()
                .forJob(myCronJobDetail())
                .withIdentity("myCronJobTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    /*@PostConstruct
    public void quartzScheduler() throws SchedulerException {*/
    @Override
    public void run(String... args){ //通过实现CommandLineRunner的run方法，可以将@Value("${}")的值获取到
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setStartupDelay(1);
//        schedulerFactoryBean.setDataSource(dataSource);
        if (triggers != null){
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            for (Trigger trigger : triggers){
                try {
                    scheduler.rescheduleJob(trigger.getKey(),trigger);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
    }
}
