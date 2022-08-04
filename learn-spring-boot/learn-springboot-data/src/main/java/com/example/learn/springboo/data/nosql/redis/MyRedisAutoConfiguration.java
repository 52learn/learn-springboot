package com.example.learn.springboo.data.nosql.redis;

import com.example.learn.springboo.data.nosql.redis.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@AutoConfiguration
public class MyRedisAutoConfiguration {

    @Bean
    MyLettuceClientConfigurationBuilderCustomizer myLettuceClientConfigurationBuilderCustomizer(){
        return new MyLettuceClientConfigurationBuilderCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //template.setKeySerializer();
        return template;
    }

    @Bean
    public RedisOperatioinExample redisOperatioinExample(RedisTemplate redisTemplate,
        StringRedisTemplate stringRedisTemplate, PersonRepository personRepository,
        ObjectMapper objectMapper){
        return new RedisOperatioinExample(redisTemplate,stringRedisTemplate,personRepository,objectMapper);

    }

    @Configuration
    @EnableRedisRepositories
    public static class RedisRepositoryConfiguration{

    }
}
