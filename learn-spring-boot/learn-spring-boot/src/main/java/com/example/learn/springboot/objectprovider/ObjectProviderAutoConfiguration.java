package com.example.learn.springboot.objectprovider;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ObjectProviderAutoConfiguration {

    @Bean
    LoggerManager loggerManager(ObjectProvider<LogService> logServices){
       return new LoggerManager(logServices);
    }

    @Bean
    LogService jsonLogger(){
        return new JsonLogger();
    }
    @Bean
    LogService plainLogger(){
        return new PlainLogger();
    }
}
