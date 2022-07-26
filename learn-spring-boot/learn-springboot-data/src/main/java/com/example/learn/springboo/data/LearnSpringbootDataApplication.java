package com.example.learn.springboo.data;

import com.example.learn.springboo.data.repository.entity.Customer;
import com.example.learn.springboo.data.repository.impl.CustomerRepository;
import com.example.learn.springboo.data.repository.impl.CustomerDaoWithJdbcTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootApplication
public class LearnSpringbootDataApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(LearnSpringbootDataApplication.class, args);

		log.info("--------------程序开始--------------");
		HoldProcessor holdProcessor = new HoldProcessor();

		log.info("--------------开始等待--------------");
		holdProcessor.startAwait();

		log.info("--------------添加shutdown hook--------------");
		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			log.info("--------------收到kill 信号，停止程序--------------");
			holdProcessor.stopAwait();
		}));
	}

	@Autowired
	CustomerDaoWithJdbcTemplate myRepository;

	@Autowired
	CustomerRepository customerRepository;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		myRepository.save();
		Customer customer = myRepository.query("XF00003");
		log.info("[Use jdbcTemplate ]query customer {customerCode=XF00003}  ,customer :{} ",customer);

		Customer insertCustomer = new Customer("XF00004", "娃哈哈集团", "0571-123456", 1, LocalDateTime.now());
		insertCustomer.setInsert(true);
		customerRepository.save(insertCustomer);

		customerRepository.findById("XF00003").ifPresent(customer1->{
			log.info("[Use jdbc repository] query customer {customerCode=XF00004}  ,customer :{} ",customer1);
		});

		List<Customer> customers = customerRepository.findByIsMember(1);
		log.info("[Use jdbc repository] query customers,customers :{} ",customers);

		boolean flag = customerRepository.update("XF00003","modify-12345");
		log.info("[Use jdbc repository] update customer :{} ",flag);

		customer.setCustomerPhone("modify-"+customer.getCustomerPhone());
		customer = customerRepository.save(customer);
		log.info("[Use jdbc repository] save customer return : {} ",customer);

	}
}
