package com.example.learn.springboot.diagnostics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class DiagnosticsApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ClassPathResource myProperties = new ClassPathResource("/META-INF/my.properties",this.getClass());
        if(!myProperties.exists()){
            throw new ProjectConstraintViolationException(myProperties.getPath()+" not exist ");
        }
    }
}