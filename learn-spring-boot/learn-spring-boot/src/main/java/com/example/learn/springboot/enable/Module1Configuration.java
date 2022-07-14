package com.example.learn.springboot.enable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module1Configuration {
    @Bean
    String moudle1Name(){
        return "moudle1";
    }

}
