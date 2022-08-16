package com.example.learn.springboo.io.task.scheduling;

import com.example.learn.springboo.io.task.scheduling.quartz.HelloWorldJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
@Configuration
public class MyQuartzServiceExample {

    @Bean
    JobDetailFactoryBean jobDetail(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName(HelloWorldJob.class.getName());
        jobDetailFactoryBean.setGroup("firstJobGroup");
        jobDetailFactoryBean.setJobClass(HelloWorldJob.class);
        jobDetailFactoryBean.setDurability(true);
        //jobDetailFactoryBean.setApplicationContextJobDataKey("applicationContext");
        return jobDetailFactoryBean;
    }
    @Bean
    CronTriggerFactoryBean cronTriggerFactoryBean(JobDetail jobDetail){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("HelloWorldJobTrigger");
        cronTriggerFactoryBean.setGroup("HelloWorldJobTriggerGroup");
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setCronExpression("0/5 * * * * ?");
        return cronTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean registerJob(CronTrigger cronTrigger){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(cronTrigger);
        schedulerFactoryBean.setAutoStartup(true);
        return schedulerFactoryBean;
    }


}
