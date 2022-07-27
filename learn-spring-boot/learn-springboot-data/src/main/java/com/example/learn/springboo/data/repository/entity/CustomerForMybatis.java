package com.example.learn.springboo.data.repository.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CustomerForMybatis implements Serializable {
    private static final long serialVersionUID = 1L;
    private String customerCode;
    private String customerName;
    private String customerPhone;
    private Integer isMember;
    private LocalDateTime createTime;
    private Integer modifierCount;

}
