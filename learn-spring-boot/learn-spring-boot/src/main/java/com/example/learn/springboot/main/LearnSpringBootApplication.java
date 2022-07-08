package com.example.learn.springboot.main;

import com.example.learn.springboot.sms.SmsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
public class LearnSpringBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LearnSpringBootApplication.class, args);
	}
	@Autowired(required = false)
	private List<SmsProvider> smsProviders;

	@Override
	public void run(String... args) throws Exception {
		log.info(" smsProviders list: {}",smsProviders);
	}
}
