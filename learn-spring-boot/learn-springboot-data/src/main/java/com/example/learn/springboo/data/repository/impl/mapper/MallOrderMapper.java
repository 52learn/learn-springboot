package com.example.learn.springboo.data.repository.impl.mapper;

import com.example.learn.springboo.data.repository.entity.MallOrderForMybatis;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MallOrderMapper {

    @Select("select * from mall_order where customer_code =  #{customerCode}")
    List<MallOrderForMybatis> queryByCustomerCode(@Param("customerCode") String customerCode);
}
