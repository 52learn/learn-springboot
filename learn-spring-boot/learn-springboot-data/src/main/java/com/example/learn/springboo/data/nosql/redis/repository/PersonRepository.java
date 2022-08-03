package com.example.learn.springboo.data.nosql.redis.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface PersonRepository extends KeyValueRepository<Person, String> {

}