package com.example.learn.springboot.custom.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "my.project")
public class MyProjectProperties {
    private String name;
    private String version;
}
