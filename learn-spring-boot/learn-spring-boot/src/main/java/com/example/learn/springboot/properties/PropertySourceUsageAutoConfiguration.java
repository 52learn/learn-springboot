package com.example.learn.springboot.properties;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@AutoConfiguration
public class PropertySourceUsageAutoConfiguration {

    @Configuration
    @PropertySource(value = "classpath:propertysource.properties")
    public class PropertySourceWithPropertiesConfiguration{

    }

    @Configuration
    @PropertySource(value = "classpath:propertysource.yaml")
    public class PropertySourceWithYamlConfiguration{

    }
}


