package com.example.learn.springboot.extension;

import com.example.learn.springboot.extension.po.Runner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * Beans not created in Spring Container
 * Usage:
 * 1. register further bean definitions; This is mainly useful if you have any third-party libraries which are not spring beans. So, these beans can be registered dynamically at runtime based on your need.
 * 2. load bean not in the classpath;
 *
 */
@Slf4j
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor{
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        log.info("=====StartUp-Lifecycle=====>>>postProcessBeanDefinitionRegistry");
        int beanDefinitionCount = registry.getBeanDefinitionCount();
        log.info("postProcessBeanDefinitionRegistry---beanDefinitionCount:{}",beanDefinitionCount);
        BeanDefinitionBuilder  bdb = BeanDefinitionBuilder.genericBeanDefinition(Runner.class);
        BeanDefinition beanDefinition1 = bdb.getBeanDefinition();
        registry.registerBeanDefinition(Runner.class.getName(), beanDefinition1);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("=====StartUp-Lifecycle=====>>>postProcessBeanFactory");
        int beanDefinitionCount = beanFactory.getBeanDefinitionCount();
        log.info("postProcessBeanFactory---beanDefinitionCount:{}",beanDefinitionCount);
    }
}
