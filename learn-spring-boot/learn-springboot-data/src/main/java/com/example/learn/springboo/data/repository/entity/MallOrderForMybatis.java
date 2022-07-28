package com.example.learn.springboo.data.repository.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MallOrderForMybatis {
    private String orderCode;
    private String orderName;
    private LocalDateTime createTime;
    private String employeeCode;
    private String employeeName;
    private String customerCode;
    private String customerName;
}
