package com.example.learn.springboot.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
@Slf4j
public class StorageApplicationListener implements GenericApplicationListener {
    private Storage storage;
    public static final String STORAGE_BEAN_NAME = "springBootStorage";

    private static final Class<?>[] EVENT_TYPES = { ApplicationStartingEvent.class,
        ApplicationEnvironmentPreparedEvent.class, ApplicationPreparedEvent.class, ContextClosedEvent.class,
        ApplicationFailedEvent.class };
    @Override
    public boolean supportsEventType(ResolvableType resolvableType) {
        return isAssignableFrom(resolvableType.getRawClass(), EVENT_TYPES);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info(" onApplicationEvent event : {}",event);
        if (event instanceof ApplicationStartingEvent) {
            onApplicationStartingEvent((ApplicationStartingEvent) event);
        }else if (event instanceof ApplicationPreparedEvent) {
            onApplicationPreparedEvent((ApplicationPreparedEvent) event);
        }
    }
    private void onApplicationStartingEvent(ApplicationStartingEvent event) {
        this.storage = Storage.get(event.getSpringApplication().getClassLoader());
    }
    private void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
        ConfigurableListableBeanFactory beanFactory = event.getApplicationContext().getBeanFactory();
        if (!beanFactory.containsBean(STORAGE_BEAN_NAME)) {
            beanFactory.registerSingleton(STORAGE_BEAN_NAME, this.storage);
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
