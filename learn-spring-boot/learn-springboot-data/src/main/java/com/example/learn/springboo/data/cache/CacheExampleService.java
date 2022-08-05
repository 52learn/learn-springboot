package com.example.learn.springboo.data.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
@Slf4j
public class CacheExampleService {

    @Cacheable("calculateResult")
    public int calculate(int num){
        log.info("calculate start ....");
        for(int i=1;i<=3;i++){
            num=num*i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("calculate end ....");
        return num;
    }
}
