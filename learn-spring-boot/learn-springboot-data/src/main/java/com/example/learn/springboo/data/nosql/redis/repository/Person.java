package com.example.learn.springboo.data.nosql.redis.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("person")
@Data
public class Person {

  @Id
  String id;
  String firstname;
  String lastname;
  Address address;
  Integer age;

  public Person() {
  }

  @Data
  public static class Address{
    private String city;
    private String street;

    public Address(String city, String street) {
      this.city = city;
      this.street = street;
    }
  }
}