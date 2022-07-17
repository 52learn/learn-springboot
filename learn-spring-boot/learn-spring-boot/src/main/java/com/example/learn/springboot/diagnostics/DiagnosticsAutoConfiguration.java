package com.example.learn.springboot.diagnostics;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DiagnosticsAutoConfiguration {

    @Bean
    ProjectConstraintViolationApplicationRunner projectConstraintViolationApplicationRunner(){
        return new ProjectConstraintViolationApplicationRunner();
    }
}
