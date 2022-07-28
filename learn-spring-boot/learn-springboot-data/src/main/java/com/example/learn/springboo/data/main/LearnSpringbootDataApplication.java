package com.example.learn.springboo.data.main;

import com.example.learn.springboo.data.HoldProcessor;
import com.example.learn.springboo.data.repository.entity.Customer;
import com.example.learn.springboo.data.repository.entity.CustomerForMybatis;
import com.example.learn.springboo.data.repository.impl.mapper.CustomerMapper;
import com.example.learn.springboo.data.repository.impl.repository.CustomerRepository;
import com.example.learn.springboo.data.repository.impl.template.CustomerDaoWithJdbcTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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

	@Autowired
	CustomerMapper customerMapper;

	@Autowired
	ApplicationContext applicationContext;
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

		int updateFlag = customerMapper.update("modify-666","XF00003");
		log.info("[Use mybatis ] update customer : {} ",updateFlag);
		CustomerForMybatis customerQueryByMybatis = customerMapper.findByCustomerCode("XF00003");
		log.info("[Use mybatis ] query customer : {} ",customerQueryByMybatis);

		List<CustomerForMybatis> customerForMybatisList = customerMapper.queryAll();
		log.info("[Use mybatis ] queryAll customer : {} ",customerForMybatisList);

		CustomerForMybatis customerForMybatisForInsert = new CustomerForMybatis();
		customerForMybatisForInsert.setCreateTime(LocalDateTime.now());
		customerForMybatisForInsert.setCustomerCode("XF00005");
		customerForMybatisForInsert.setCustomerName("腾讯科技");
		customerForMybatisForInsert.setCustomerPhone("0772-32312222");
		customerForMybatisForInsert.setIsMember(1);
		int saveFlag = customerMapper.insert(customerForMybatisForInsert);
		log.info("[Use mybatis ] insert customer : {} ",saveFlag);

	}
}
