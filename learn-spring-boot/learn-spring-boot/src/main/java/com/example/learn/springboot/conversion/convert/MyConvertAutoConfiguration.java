package com.example.learn.springboot.conversion.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class MyConvertAutoConfiguration {
    @Bean
    public StringToLaptopConvert stringToLaptopConvert(ObjectMapper objectMapper){
        return new StringToLaptopConvert(objectMapper);
    }

    @Bean
    MyConvertController myConvertController(){
        return new MyConvertController();
    }
}
