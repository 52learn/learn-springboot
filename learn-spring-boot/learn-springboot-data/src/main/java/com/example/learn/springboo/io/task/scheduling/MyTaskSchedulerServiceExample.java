package com.example.learn.springboo.io.task.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.LocalDateTime;

@Slf4j
public class MyTaskSchedulerServiceExample implements InitializingBean {

    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public void schedule(){
        threadPoolTaskScheduler.scheduleAtFixedRate(()->{
            log.info("schedule ... scheduleAtFixedRate : {}", LocalDateTime.now());
        },5000);
    }

    @Scheduled(fixedDelay = 3000)
    public void scheduleByAnnotation(){
        log.info("scheduleByAnnotation : {}", LocalDateTime.now());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.schedule();
    }
}
