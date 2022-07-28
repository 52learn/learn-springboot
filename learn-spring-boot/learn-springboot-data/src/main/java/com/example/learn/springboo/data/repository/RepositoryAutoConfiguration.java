package com.example.learn.springboo.data.repository;

import com.example.learn.springboo.data.repository.customizer.MyConfigurationCustomizer;
import com.example.learn.springboo.data.repository.customizer.MySqlSessionFactoryBeanCustomizer;
import com.example.learn.springboo.data.repository.impl.template.CustomerDaoWithJdbcTemplate;
import com.example.learn.springboo.data.repository.interceptor.EncryptDecryptInterceptor;
import com.example.learn.springboo.data.repository.interceptor.ModifyCountAutoIncrementInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

@AutoConfiguration
public class RepositoryAutoConfiguration {

    @Bean
    CustomerDaoWithJdbcTemplate customerDaoWithJdbcTemplate(JdbcTemplate jdbcTemplate){
        return new CustomerDaoWithJdbcTemplate(jdbcTemplate);
    }

    @Configuration
    @EnableJdbcRepositories
    public static class JdbcRepositoryConfiguration{

    }
    @Configuration
    @MapperScan(value = "com.example.learn.springboo.data.repository.impl.mapper")
    public static class MybatisConfiguration{
        @Bean
        ModifyCountAutoIncrementInterceptor modifyCountAutoIncrementInterceptor(){
            return new ModifyCountAutoIncrementInterceptor();
        }
        @Bean
        EncryptDecryptInterceptor encryptDecryptInterceptor(){
            return new EncryptDecryptInterceptor();
        }

        @Bean
        MySqlSessionFactoryBeanCustomizer mySqlSessionFactoryBeanCustomizer(){
            return new MySqlSessionFactoryBeanCustomizer();
        }
        @Bean
        MyConfigurationCustomizer myConfigurationCustomizer(){
            return new MyConfigurationCustomizer();
        }
    }


}
