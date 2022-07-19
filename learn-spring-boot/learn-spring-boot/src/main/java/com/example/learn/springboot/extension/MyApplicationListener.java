package com.example.learn.springboot.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
@Slf4j
public class MyApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("=====StartUp-Lifecycle=====>>>callback: ApplicationListener.onApplicationEvent eventClass:{},sourceClass:{}",event.getClass().getSimpleName(),event.getSource().getClass().getSimpleName());
    }
}
