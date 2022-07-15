package com.example.learn.springboot.enable;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class SomeBeanConfigurationRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder  bdb = BeanDefinitionBuilder.genericBeanDefinition(Module1ServiceImpl.class);
        BeanDefinition beanDefinition1 = bdb.getBeanDefinition();
        registry.registerBeanDefinition(Module1Service.class.getName(), beanDefinition1);
    }
}
