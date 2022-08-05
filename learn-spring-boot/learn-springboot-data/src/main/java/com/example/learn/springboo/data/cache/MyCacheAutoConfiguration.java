package com.example.learn.springboo.data.cache;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
@EnableCaching
@AutoConfiguration
public class MyCacheAutoConfiguration {
    @Bean
    public CacheExampleService cacheExampleService(){
        return new CacheExampleService();
    }
    @Bean
    public CacheExample cacheExample(){
        return new CacheExample();
    }
}
