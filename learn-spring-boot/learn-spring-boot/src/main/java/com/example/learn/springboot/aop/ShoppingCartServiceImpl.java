package com.example.learn.springboot.aop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService{
    @Override
    public Double totalMoney() {
        log.info("totalMoney: 100");
        return 100d;
    }
}
