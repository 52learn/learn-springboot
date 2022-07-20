package com.example.learn.springboot.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class WebController {

    @GetMapping(value = "/person")
    public Person getPerson(){
        return new Person("kim-get",27);
    }

    @PostMapping(value = "/person")
    public Person postPerson(@RequestBody Person person){
        person.setName("kim-post");
        person.setAge(30);
        return person;
    }

    @ToString
    @Getter
    @Setter
    public static class Person{
        private String name;
        private int age;

        public Person(){

        }
        public Person(String name,int age){
            this.name = name;
            this.age = age;
        }
    }
}
