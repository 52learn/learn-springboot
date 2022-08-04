package com.example.learn.springboo.data.repository;

import com.example.learn.springboo.data.repository.customizer.MyConfigurationCustomizer;
import com.example.learn.springboo.data.repository.customizer.MySqlSessionFactoryBeanCustomizer;
import com.example.learn.springboo.data.repository.impl.mapper.MallOrderMapper;
import com.example.learn.springboo.data.repository.impl.template.CustomerDaoWithJdbcTemplate;
import com.example.learn.springboo.data.repository.interceptor.EncryptDecryptInterceptor;
import com.example.learn.springboo.data.repository.interceptor.ModifyCountAutoIncrementInterceptor;
import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
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
    @Bean
    MySqlOperationExample mySqlOperationExample(){
        return new MySqlOperationExample();
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

        @Bean
        MapperFactoryBean<MallOrderMapper> mallOrderMapperFactoryBean(SqlSessionFactory sqlSessionFactory){
            MapperFactoryBean<MallOrderMapper> mallOrderMapperFactoryBean = new MapperFactoryBean<>(MallOrderMapper.class);
            mallOrderMapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
            return mallOrderMapperFactoryBean;
        }

    }


}
