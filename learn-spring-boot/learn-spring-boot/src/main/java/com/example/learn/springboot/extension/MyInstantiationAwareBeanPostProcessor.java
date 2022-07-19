package com.example.learn.springboot.extension;

import com.example.learn.springboot.extension.po.Runner;
import com.example.learn.springboot.main.LearnSpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

/**
 * Usage；
 * 1. 实例化后，创建proxy代理类
 * 2. postProcessProperties 可用于属性注入
 */
@Slf4j
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor, PriorityOrdered {
    private final BeanFactory beanFactory;

    public MyInstantiationAwareBeanPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if(Runner.class.isAssignableFrom(beanClass)){
            log.info("=====StartUp-Lifecycle=====>>>callback: InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(bean instanceof Runner){
            log.info("=====StartUp-Lifecycle=====>>>callback: InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation");
        }
        return true;
    }

    /**
     * bean已经实例化完成，在属性注入时阶段触发，@Autowired,@Resource等注解原理基于此方法实现
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
        throws BeansException {
        if(bean instanceof Runner){
            log.info("=====StartUp-Lifecycle=====>>>callback: InstantiationAwareBeanPostProcessor.postProcessProperties");
        }
        if(bean instanceof LearnSpringBootApplication){
            log.info("");
        }
        return null;
    }

    /**
     * 初始化bean之前：Invoked after population of normal bean properties , before InitializingBean's {@code afterPropertiesSet} or a custom init-method , 注入spring 容器之前
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof Runner){
            log.info("=====StartUp-Lifecycle=====>>>callback: InstantiationAwareBeanPostProcessor.postProcessBeforeInitialization");
        }
        return bean;
    }

    /**
     * 初始化bean之后：after InitializingBean's {@code afterPropertiesSet} or a custom init-method , 注入spring容器之后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof Runner){
            log.info("=====StartUp-Lifecycle=====>>>callback: InstantiationAwareBeanPostProcessor.postProcessAfterInitialization");
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return  Ordered.LOWEST_PRECEDENCE;
    }
}
