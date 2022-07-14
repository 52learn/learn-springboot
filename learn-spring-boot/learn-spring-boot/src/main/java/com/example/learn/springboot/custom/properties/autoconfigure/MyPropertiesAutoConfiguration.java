package com.example.learn.springboot.custom.properties.autoconfigure;

import com.example.learn.springboot.custom.properties.MyProjectProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(value = MyProjectProperties.class)
public class MyPropertiesAutoConfiguration {

}
