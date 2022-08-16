package com.example.learn.springboo.io.task;

import com.example.learn.springboo.io.task.scheduling.MyQuartzServiceExample;
import com.example.learn.springboo.io.task.scheduling.MyTaskSchedulerServiceExample;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@AutoConfiguration
@EnableScheduling
@Import(value = {MyQuartzServiceExample.class})
public class TaskAutoConfiguration {

    @Bean
    MyTaskSchedulerServiceExample myTaskSchedulerServiceExample(){
        return new MyTaskSchedulerServiceExample();
    }

}
