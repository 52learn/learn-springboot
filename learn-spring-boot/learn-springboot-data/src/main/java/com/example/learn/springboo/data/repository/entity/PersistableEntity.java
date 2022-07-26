package com.example.learn.springboo.data.repository.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;
@Data
public class PersistableEntity {
    @Transient
    protected boolean insert;

}
