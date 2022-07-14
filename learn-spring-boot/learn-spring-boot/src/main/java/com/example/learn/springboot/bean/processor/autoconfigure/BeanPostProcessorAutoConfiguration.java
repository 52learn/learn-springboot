package com.example.learn.springboot.bean.processor.autoconfigure;

import com.example.learn.springboot.bean.processor.MyBeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
@AutoConfiguration
public class BeanPostProcessorAutoConfiguration {
    @Bean
    MyBeanPostProcessor myBeanPostProcessor(){
        return new MyBeanPostProcessor();
    }
}
