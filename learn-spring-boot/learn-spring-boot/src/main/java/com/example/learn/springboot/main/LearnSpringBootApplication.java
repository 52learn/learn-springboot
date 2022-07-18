package com.example.learn.springboot.main;

import com.example.learn.springboot.custom.properties.MyProjectProperties;
import com.example.learn.springboot.enable.*;
import com.example.learn.springboot.i18n.I18nMessagePrinter;
import com.example.learn.springboot.importest.OrderService;
import com.example.learn.springboot.properties.CustomerProperties;
import com.example.learn.springboot.sms.SmsProvider;
import com.example.learn.springboot.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableSomeBeanConfiguration
@EnableConfigurationImportSelector(configurations = {Module1Configuration.class, Module2Configuration.class})
@EnableSomeBeanRegistrar
@EnableConfigurationProperties(value = {CustomerProperties.class})
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

	@Autowired
	private OrderService orderService;

	@Autowired
	@Qualifier("bean1")
	private String bean1;

	@Autowired
	private String moudle1Name;
	@Autowired
	private String moudle2Name;

	@Autowired
	private Module1Service module1Service;

	@Autowired
	YamlMapFactoryBean yamlMapFactoryBean;

	@Autowired
	YamlPropertiesFactoryBean yamlPropertiesFactoryBean;

	@Value("${bicycle.brand:}")
	String bicycleBrand;
	@Value("${car.brand:}")
	String carBrand;

	@Autowired
	CustomerProperties customerProperties;

	@Autowired
	I18nMessagePrinter i18nMessagePrinter;

	@Override
	public void run(String... args) throws Exception {
		log.debug("debug info -----");
		log.info("---------- smsProviders list: {}",smsProviders);
		log.info("---------- env  is ：{}",env);
		log.info("----------  activeProfile：{}",activeProfile);
		//Storage storage = Storage.get(this.getClass().getClassLoader());
		storage.save("test content");

		log.info("load properties from classpath:META-INF/my.properties, myProjectProperties:{} ",myProjectProperties);
		log.info("orderService.makeOrder() :{}",orderService.makeOrder());
		log.info("@EnableSomeBeanConfiguration test.... :{}",bean1);
		log.info("@EnableConfigurationSelector test.... :{},{}",moudle1Name,moudle2Name);
		log.info("@EnableSomeBeanRegistrar test.... module1Service :{}",module1Service);

		Map<String, Object> yamlMap = yamlMapFactoryBean.getObject();
		log.info("Direct load yaml file to map: {}",yamlMap);

		Properties yamlProperties = yamlPropertiesFactoryBean.getObject();
		log.info("Direct load yaml file to properties: {}",yamlProperties);
		log.info("bicycle.brand ：{}",bicycleBrand);
		log.info("car.brand ：{}",carBrand);

		log.info("customerProperties : {}",customerProperties);

		i18nMessagePrinter.print();
	}


}
