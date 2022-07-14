package com.example.learn.springboot.enable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module2Configuration {
    @Bean
    String moudle2Name(){
        return "moudle2";
    }

}
