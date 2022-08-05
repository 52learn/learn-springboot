package com.example.learn.springboo.data.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class CacheExample {
    @Autowired
    CacheExampleService  cacheExampleService;


    public void invoke(){
        int num = 5;
        int result = cacheExampleService.calculate(num);
        log.info("cacheExampleService.calculate param:{},result:{}",num,result);
        result = cacheExampleService.calculate(num);
        log.info("cacheExampleService.calculate param:{},result:{}",num,result);
        num = 10;
        result = cacheExampleService.calculate(num);
        log.info("cacheExampleService.calculate param:{},result:{}",num,result);
    }
}
