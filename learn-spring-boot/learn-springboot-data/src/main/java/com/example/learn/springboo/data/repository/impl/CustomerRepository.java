package com.example.learn.springboo.data.repository.impl;

import com.example.learn.springboo.data.repository.entity.Customer;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,String> {

    List<Customer> findByIsMember(Integer member);

    @Modifying
    @Query("update customer set customer_phone=:customerPhone where customer_code=:customerCode")
    boolean update(@Param("customerCode") String customerCode,@Param("customerPhone") String customerPhone);

}
