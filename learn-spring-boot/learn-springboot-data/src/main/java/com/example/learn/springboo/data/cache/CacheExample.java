package com.example.learn.springboo.data.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;

@Slf4j
public class CacheExample {
    @Autowired
    CacheExampleService  cacheExampleService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    public void invoke(){
        clearRedis();
        int num = 5;
        int result = cacheExampleService.calculate(num);
        log.info("cacheExampleService.calculate param:{},result:{}",num,result);
        result = cacheExampleService.calculate(num);
        log.info("cacheExampleService.calculate param:{},result:{}",num,result);
        num = 10;
        result = cacheExampleService.calculate(num);
        log.info("cacheExampleService.calculate param:{},result:{}",num,result);
    }

    private void clearRedis(){
        log.info("clearRedis start............");
        Set<String> keys = stringRedisTemplate.keys("*calculateResult*");
        long count = stringRedisTemplate.delete(keys);
        log.info("stringRedisTemplate.delete keys count :{}",count);
        log.info("clearRedis end..............");
    }
}
