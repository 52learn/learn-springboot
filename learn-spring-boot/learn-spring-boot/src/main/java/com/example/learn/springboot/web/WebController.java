package com.example.learn.springboot.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

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

    @PostMapping(value = "/person/properties")
    public Properties postPerson(@RequestBody Properties person){
        String name = person.getProperty("person.name");
        String age = person.getProperty("person.age");
        person.setProperty("person.name",name+"-changed");
        person.setProperty("person.age",age+"-changed");
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
