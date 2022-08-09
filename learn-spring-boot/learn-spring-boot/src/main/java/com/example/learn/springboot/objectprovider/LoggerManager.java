package com.example.learn.springboot.objectprovider;

import org.springframework.beans.factory.ObjectProvider;

public class LoggerManager implements LogService{
    private ObjectProvider<LogService> logServices;

    public LoggerManager(ObjectProvider<LogService> logServices){
        this.logServices = logServices;
    }

    @Override
    public void log(String data) {
        logServices.orderedStream().forEach((logService)->{
            if(!(logService instanceof LoggerManager)){
                logService.log(data);
            }
        });
    }
}
