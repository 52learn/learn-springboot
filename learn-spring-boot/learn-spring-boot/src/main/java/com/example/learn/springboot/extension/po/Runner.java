package com.example.learn.springboot.extension.po;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

@Slf4j
@Getter
@Setter
public class Runner implements SmartInitializingSingleton, BeanFactoryAware, BeanNameAware, BeanClassLoaderAware,
    EnvironmentAware, ResourceLoaderAware, ApplicationEventPublisherAware,
    MessageSourceAware, ApplicationContextAware, ServletContextAware, InitializingBean {
    String name;
    int weight;
    private BeanFactory beanFactory;
    @Override
    public String toString(){
        return "[name:"+name+",weight:"+weight+"]";
    }



    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("=====StartUp-Lifecycle=====>>>callback: BeanFactoryAware.setBeanFactory");
        this.beanFactory = beanFactory;
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("=====StartUp-Lifecycle=====>>>callback: ApplicationContextAware.setApplicationContext");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        log.info("=====StartUp-Lifecycle=====>>>callback: ApplicationEventPublisherAware.setApplicationEventPublisher");

    }

    @Override
    public void setEnvironment(Environment environment) {
        log.info("=====StartUp-Lifecycle=====>>>callback: EnvironmentAware.setEnvironment");

    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        log.info("=====StartUp-Lifecycle=====>>>callback: MessageSourceAware.setMessageSource");
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        log.info("=====StartUp-Lifecycle=====>>>callback: ResourceLoaderAware.setResourceLoader");
    }

    @Override
    public void setBeanName(String name) {
        log.info("=====StartUp-Lifecycle=====>>>callback: BeanNameAware.setBeanName:{}",name);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        log.info("=====StartUp-Lifecycle=====>>>callback: BeanClassLoaderAware.setBeanClassLoader");
    }
    @Override
    public void setServletContext(ServletContext servletContext) {
        log.info("=====StartUp-Lifecycle=====>>>callback: ServletContextAware.setServletContext");
    }
    @PostConstruct
    public void init(){
        log.info("=====StartUp-Lifecycle=====>>>callback: init()  [Initialization use @PostConstruct]  ");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("=====StartUp-Lifecycle=====>>>callback: InitializingBean.afterPropertiesSet()  [Initialization implements InitializingBean]  ");
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info("=====StartUp-Lifecycle=====>>>callback: SmartInitializingSingleton.afterSingletonsInstantiated");
    }


}
