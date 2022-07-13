package com.example.learn.springboot.custom.properties;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(value = MyProjectProperties.class)
public class MyPropertiesAutoConfiguration {

}
