package com.example.learn.springboot.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * callback before Spring container refresh,Typically used within web applications that require some programmatic initialization
 * of the application context;
 *
 * Usage:
 * 1. registering property sources or activating profiles
 */
@Slf4j
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("=====StartUp-Lifecycle=====>>>initialize");
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        Map<String, Object> map = new HashMap<>();
        map.put("initialize.time", LocalDateTime.now());
        MapPropertySource mapPropertySource = new MapPropertySource("MyApplicationContextInitializer", map);
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addLast(mapPropertySource);
    }
}
