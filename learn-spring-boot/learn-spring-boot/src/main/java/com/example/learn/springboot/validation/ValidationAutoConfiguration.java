package com.example.learn.springboot.validation;

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

    @RestController
    @Validated
    public static class UserController{

        @PostMapping("/regist")
        public String regist(@RequestBody  @Valid User user){
            return "regist ok";
        }
    }
}
