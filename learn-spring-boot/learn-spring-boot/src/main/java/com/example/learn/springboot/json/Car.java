package com.example.learn.springboot.json;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Car {
    private String brand;
    private int usedYears;
    private LocalDateTime productionTime;

}
