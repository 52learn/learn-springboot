package com.example.learn.springboo.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@SpringBootApplication
public class LearnSpringbootDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnSpringbootDataApplication.class, args);
	}

}
