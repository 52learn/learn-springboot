package com.example.learn.springboot.bean.processor;

import com.example.learn.springboot.main.LearnSpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Use BeanPostProcessor to extend application
 */
@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LearnSpringBootApplication ) {
           log.info("postProcessAfterInitialization ...bean: LearnSpringBootApplication");
        }
        return bean;
    }
}
