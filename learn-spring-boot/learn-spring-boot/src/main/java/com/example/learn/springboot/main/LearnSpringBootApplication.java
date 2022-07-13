package com.example.learn.springboot.main;

import com.example.learn.springboot.custom.properties.MyProjectProperties;
import com.example.learn.springboot.sms.SmsProvider;
import com.example.learn.springboot.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
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

	@Autowired(required = false)
	private String env;

	@Value("${spring.profiles.active:}")
	private String activeProfile;

	@Autowired
	private Storage storage;

	/*@Value("${my.project.name:}")
	private */

	@Autowired
	private MyProjectProperties myProjectProperties;


	@Override
	public void run(String... args) throws Exception {
		log.info("---------- smsProviders list: {}",smsProviders);
		log.info("---------- env  is ：{}",env);
		log.info("----------  activeProfile：{}",activeProfile);
		//Storage storage = Storage.get(this.getClass().getClassLoader());
		storage.save("test content");

		log.info("load properties from classpath:META-INF/my.properties, myProjectProperties:{} ",myProjectProperties);
	}


}
