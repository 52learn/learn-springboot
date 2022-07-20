package com.example.learn.springboot.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AutoConfiguration
public class ValidationAutoConfiguration {


    @Bean
    UserRepository userRepository(){
        return new UserRepository();
    }

    @Slf4j
    @RestController
    @Validated
    public static class UserController{

        @PostMapping("/regist")
        public String regist(@RequestBody  @Valid User user){
            log.info("user:{}",user);
            return "regist ok";
        }
    }
}
