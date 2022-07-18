package com.example.learn.springboot.properties.validator;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(ReportConfigProperties.class)
public class ReportConfigPropertiesAutoConfiguration {

   /* @Bean
    public static ReportConfigPropertiesValidator configurationPropertiesValidator(){
        return new ReportConfigPropertiesValidator();
    }*/
}
