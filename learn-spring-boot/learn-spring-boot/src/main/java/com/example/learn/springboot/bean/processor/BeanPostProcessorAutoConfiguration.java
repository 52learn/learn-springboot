package com.example.learn.springboot.bean.processor;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
@AutoConfiguration
public class BeanPostProcessorAutoConfiguration {
    @Bean
    MyBeanPostProcessor myBeanPostProcessor(){
        return new MyBeanPostProcessor();
    }
}
