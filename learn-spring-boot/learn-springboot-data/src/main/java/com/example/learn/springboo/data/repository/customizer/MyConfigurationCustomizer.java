package com.example.learn.springboo.data.repository.customizer;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
@Slf4j
public class MyConfigurationCustomizer implements ConfigurationCustomizer {
    @Override
    public void customize(Configuration configuration) {
        log.info("customize configuration....");
    }
}
