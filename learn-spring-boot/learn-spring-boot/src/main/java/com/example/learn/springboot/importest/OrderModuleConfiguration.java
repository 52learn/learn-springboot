package com.example.learn.springboot.importest;

import org.springframework.context.annotation.Bean;

public class OrderModuleConfiguration {
    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl();
    }
}
