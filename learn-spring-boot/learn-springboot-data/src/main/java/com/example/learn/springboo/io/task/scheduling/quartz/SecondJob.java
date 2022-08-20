package com.example.learn.springboo.io.task.scheduling.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class SecondJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Spring_Quartz SecondJob!"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
    }
}
