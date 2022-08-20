package com.example.learn.springboo.io.task.scheduling;

import com.example.learn.springboo.io.task.scheduling.quartz.HelloWorldJob;
import com.example.learn.springboo.io.task.scheduling.quartz.SecondJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.sql.SelectBuilder;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
@Configuration
public class MyQuartzServiceExample {

    @Bean
    JobDetailFactoryBean hellowWorldJobDetail(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName(HelloWorldJob.class.getName());
        jobDetailFactoryBean.setGroup("jobGroup");
        jobDetailFactoryBean.setJobClass(HelloWorldJob.class);
        jobDetailFactoryBean.setDurability(true);
        //jobDetailFactoryBean.setApplicationContextJobDataKey("applicationContext");
        return jobDetailFactoryBean;
    }
    @Bean
    JobDetailFactoryBean secondJobDetail(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName(SecondJob.class.getName());
        jobDetailFactoryBean.setGroup("jobGroup");
        jobDetailFactoryBean.setJobClass(SecondJob.class);
        jobDetailFactoryBean.setDurability(true);
        //jobDetailFactoryBean.setApplicationContextJobDataKey("applicationContext");
        return jobDetailFactoryBean;
    }
    @Bean
    CronTriggerFactoryBean helloWorldJobCronTrigger(JobDetail hellowWorldJobDetail){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("helloWorldJobCronTrigger");
        cronTriggerFactoryBean.setGroup("helloWorldJobCronTriggerGroup");
        cronTriggerFactoryBean.setJobDetail(hellowWorldJobDetail);
        cronTriggerFactoryBean.setCronExpression("0/5 * * * * ?");
        return cronTriggerFactoryBean;
    }
    @Bean
    CronTriggerFactoryBean secondJobCronTrigger(JobDetail secondJobDetail){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("secondJobCronTrigger");
        cronTriggerFactoryBean.setGroup("secondJobCronTriggerGroup");
        cronTriggerFactoryBean.setJobDetail(secondJobDetail);
        cronTriggerFactoryBean.setCronExpression("0/5 * * * * ?");
        return cronTriggerFactoryBean;
    }
    @Bean
    public SchedulerFactoryBean registerJob(CronTrigger helloWorldJobCronTrigger,CronTrigger secondJobCronTrigger){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(helloWorldJobCronTrigger,secondJobCronTrigger);
        schedulerFactoryBean.setAutoStartup(true);
        return schedulerFactoryBean;
    }


}
