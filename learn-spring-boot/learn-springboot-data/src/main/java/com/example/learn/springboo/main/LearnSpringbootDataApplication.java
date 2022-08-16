package com.example.learn.springboo.main;

import com.example.learn.springboo.data.HoldProcessor;
import com.example.learn.springboo.data.cache.CacheExample;
import com.example.learn.springboo.data.nosql.redis.RedisOperatioinExample;
import com.example.learn.springboo.data.repository.MySqlOperationExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class LearnSpringbootDataApplication implements ApplicationRunner {

	@Autowired
	RedisOperatioinExample redisOperatioinExample;
	@Autowired
	MySqlOperationExample mySqlOperationExample;
	@Autowired
	CacheExample cacheExample;

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


	@Override
	public void run(ApplicationArguments args) throws Exception {
		//mySqlOperationExample.apiInvoke();
		//redisOperatioinExample.apiInvoke();
		//cacheExample.invoke();

	}


}
