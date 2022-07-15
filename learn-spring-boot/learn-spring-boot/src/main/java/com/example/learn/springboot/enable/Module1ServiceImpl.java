package com.example.learn.springboot.enable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Module1ServiceImpl implements Module1Service{
    @Override
    public void process() {
        log.info("Module1ServiceImpl.process() .... ");
    }
}
