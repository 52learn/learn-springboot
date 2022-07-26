package com.example.learn.springboo.data.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("CUSTOMER")
public class Customer extends PersistableEntity implements Persistable<String>, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String customerCode;
    private String customerName;
    private String customerPhone;
    private Integer isMember;
    private LocalDateTime createTime;


    @Override
    @JsonIgnore
    public String getId() {
        return customerCode;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return insert;
    }
}
