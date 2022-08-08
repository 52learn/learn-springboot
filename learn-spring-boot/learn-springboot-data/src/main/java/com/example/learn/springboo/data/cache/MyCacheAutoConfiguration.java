package com.example.learn.springboo.data.cache;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
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

    /*@Bean
    public ConcurrentMapCache concurrentMapCache(){
        ConcurrentMapCache cache = new ConcurrentMapCache("calculateResult");
        return cache;
    }*/
}
