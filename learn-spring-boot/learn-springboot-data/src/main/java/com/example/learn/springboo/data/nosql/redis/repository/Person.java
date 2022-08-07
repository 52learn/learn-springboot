package com.example.learn.springboo.data.nosql.redis.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "person",timeToLive = 60)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

  @Id
  String id;
  String firstname;
  String lastname;
  Address address;
  Integer age;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Address{
    private String city;
    private String street;
  }
}