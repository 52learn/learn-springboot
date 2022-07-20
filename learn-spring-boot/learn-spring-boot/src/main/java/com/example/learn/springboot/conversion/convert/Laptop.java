package com.example.learn.springboot.conversion.convert;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Laptop {
    private String brand;
    private Double inch;
    private List<Output> outputs;

    @Getter
    @Setter
    @ToString
    public static class Output{
        private String name;
    }
}
