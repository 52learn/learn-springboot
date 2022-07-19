package com.example.learn.springboot.extension.po;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Programmer {
    private String name;
    public void work(){
        log.info("Programmer : {} is working ....",name);
    }
}
