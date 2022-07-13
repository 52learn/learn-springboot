package com.example.learn.springboot.custom.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

@Slf4j
public class PropertiesLoadApplicationListener implements GenericApplicationListener {
    private static final Class<?>[] EVENT_TYPES = { ApplicationEnvironmentPreparedEvent.class};
    @Override
    public boolean supportsEventType(ResolvableType resolvableType) {
        return isAssignableFrom(resolvableType.getRawClass(), EVENT_TYPES);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            log.info(" onApplicationEvent event : {}",event);
            ConfigurableEnvironment configurableEnvironment = ((ApplicationEnvironmentPreparedEvent)event).getEnvironment();
            Resource location = new ClassPathResource("META-INF/my.properties");
            try {
                Properties properties = PropertiesLoaderUtils.loadProperties(new EncodedResource(location, Charset.forName("UTF-8")));
                PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource("myProperties",properties);
                configurableEnvironment.getPropertySources().addFirst(propertiesPropertySource);
            } catch (IOException e) {
                log.warn("classpath:META-INF/my.properties not exist ...... ");
            }
        }
    }

    private boolean isAssignableFrom(Class<?> type, Class<?>... supportedTypes) {
        if (type != null) {
            for (Class<?> supportedType : supportedTypes) {
                if (supportedType.isAssignableFrom(type)) {
                    return true;
                }
            }
        }
        return false;
    }
}
