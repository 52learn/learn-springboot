package com.example.learn.springboo.data.nosql.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
@Slf4j
public class MyLettuceClientConfigurationBuilderCustomizer implements LettuceClientConfigurationBuilderCustomizer {
    @Override
    public void customize(LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigurationBuilder) {
        log.info("com.example.learn.springboo.data.nosql.redis.MyLettuceClientConfigurationBuilderCustomizer.customize .....");
    }
}
