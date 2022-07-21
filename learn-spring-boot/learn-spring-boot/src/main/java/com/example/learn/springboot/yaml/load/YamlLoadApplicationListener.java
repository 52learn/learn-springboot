package com.example.learn.springboot.yaml.load;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Slf4j
public class YamlLoadApplicationListener implements GenericApplicationListener {
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
            Resource location = new ClassPathResource("META-INF/load-yaml-as-property-source.yaml");
            PropertySource propertySource = getPropertySourceByYamlPropertiesFactoryBean(location);
            Optional.ofNullable(propertySource).ifPresent(configurableEnvironment.getPropertySources()::addFirst);
        }
    }

    private PropertySource<?> getPropertySourceByYamlPropertiesFactoryBean(Resource resource){
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);
        Properties properties = factory.getObject();
        return new PropertiesPropertySource(resource.getFilename(), properties);
    }

    private PropertySource<?> getPropertySourceByYamlPropertySourceLoader(Resource resource){
        try {
        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        List<PropertySource<?>> propertySources = yamlPropertySourceLoader.load("load-yaml-as-property-source",resource);
        return propertySources.get(0);
        } catch (IOException e) {
            log.warn("classpath:META-INF/load-yaml-as-property-source.yaml not exist ...... ");
        }
        return null;
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
