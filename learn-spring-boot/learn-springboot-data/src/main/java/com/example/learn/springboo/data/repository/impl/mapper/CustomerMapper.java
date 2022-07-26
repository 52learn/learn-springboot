package com.example.learn.springboo.data.repository.impl.mapper;

import com.example.learn.springboo.data.repository.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CustomerMapper {

  @Select("SELECT * FROM customer WHERE customer_code = #{customerCode}")
  Customer findByCustomerCode(@Param("customerCode") String customerCode);

}