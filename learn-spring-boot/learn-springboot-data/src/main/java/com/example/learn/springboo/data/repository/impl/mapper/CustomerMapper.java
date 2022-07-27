package com.example.learn.springboo.data.repository.impl.mapper;

import com.example.learn.springboo.data.repository.entity.CustomerForMybatis;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {
  /*
  若配置了mybatis.configuration.map-underscore-to-camel-case=true 那么无需显示配置@Results
  @Results({
      @Result(property = "customerCode", column = "customer_code"),
      @Result(property = "customerName", column = "customer_name"),
      @Result(property = "customerPhone", column = "customer_phone"),
      @Result(property = "isMember", column = "is_member"),
      @Result(property = "createTime", column = "create_time")
  })*/
  @Select("SELECT * FROM customer WHERE customer_code = #{customerCode}")
  CustomerForMybatis findByCustomerCode(@Param("customerCode") String customerCode);

  @Update("update customer set customer_phone=#{customerPhone} where customer_code = #{customerCode}")
  int update(@Param("customerPhone") String customerPhone,@Param("customerCode") String customerCode);

  @Select("SELECT * FROM customer")
  List<CustomerForMybatis> queryAll();

  @Insert("insert into customer(`customer_code`,`customer_name`,`customer_phone`,`is_member`,`create_time`) "
      + " values(#{customerCode},#{customerName},#{customerPhone},#{isMember},#{createTime})")
  int insert(CustomerForMybatis customer);
}