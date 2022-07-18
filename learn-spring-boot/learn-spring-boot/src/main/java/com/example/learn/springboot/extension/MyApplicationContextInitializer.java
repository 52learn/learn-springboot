package com.example.learn.springboot.extension;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * callback before Spring container refresh,Typically used within web applications that require some programmatic initialization
 * of the application context;
 *
 * Usage:
 * 1. registering property sources or activating profiles
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        Map<String, Object> map = new HashMap<>();
        map.put("key01", "value01");
        MapPropertySource mapPropertySource = new MapPropertySource("MyApplicationContextInitializer", map);
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addLast(mapPropertySource);
    }
}
