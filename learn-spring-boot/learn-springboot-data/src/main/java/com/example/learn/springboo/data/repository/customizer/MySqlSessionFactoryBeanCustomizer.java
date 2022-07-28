package com.example.learn.springboo.data.repository.customizer;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SqlSessionFactoryBeanCustomizer;
@Slf4j
public class MySqlSessionFactoryBeanCustomizer implements SqlSessionFactoryBeanCustomizer {
    @Override
    public void customize(SqlSessionFactoryBean factoryBean) {
        log.info("customize SqlSessionFactoryBean...");
    }
}
