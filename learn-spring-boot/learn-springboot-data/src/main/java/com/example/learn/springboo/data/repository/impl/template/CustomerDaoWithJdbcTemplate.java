package com.example.learn.springboo.data.repository.impl.template;

import com.example.learn.springboo.data.repository.entity.Customer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

public class CustomerDaoWithJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDaoWithJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(){
        String sql ="insert into customer (customer_code,customer_name,customer_phone,is_member,create_time) "
            + "values (?,?,?,?,?)";
        this.jdbcTemplate.update(sql,new Object[]{"XF00003","杭钢集团","0571-33333333",1,new Date()});
    }

    public Customer query(String customerCode){
       return this.jdbcTemplate.queryForObject ("select * from customer where customer_code=?",
           new BeanPropertyRowMapper<>(Customer.class),
           new Object[]{customerCode});
    }
}
