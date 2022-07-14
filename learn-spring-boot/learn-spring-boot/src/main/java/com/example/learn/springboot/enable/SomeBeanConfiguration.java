package com.example.learn.springboot.enable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SomeBeanConfiguration {
    @Bean
    String bean1(){
        return "bean1";
    }

}
