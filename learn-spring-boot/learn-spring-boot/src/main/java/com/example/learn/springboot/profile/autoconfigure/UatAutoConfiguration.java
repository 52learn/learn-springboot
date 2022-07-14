package com.example.learn.springboot.profile.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile(value = "uat")
@AutoConfiguration
public class UatAutoConfiguration {

    @Bean(name = "env")
    String env(){
        return "uat";
    }
}
