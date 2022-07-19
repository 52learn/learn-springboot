package com.example.learn.springboot.extension;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ExtensionAutoConfiguration {
    @Bean
    MyBeanDefinitionRegistryPostProcessor myBeanDefinitionRegistryPostProcessor(){
        return new MyBeanDefinitionRegistryPostProcessor();
    }

}
