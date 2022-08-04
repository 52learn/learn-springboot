package com.example.learn.springboo.data.main;

import com.example.learn.springboo.data.HoldProcessor;
import com.example.learn.springboo.data.nosql.redis.repository.Person;
import com.example.learn.springboo.data.nosql.redis.repository.PersonRepository;
import com.example.learn.springboo.data.repository.entity.Customer;
import com.example.learn.springboo.data.repository.entity.CustomerForMybatis;
import com.example.learn.springboo.data.repository.entity.MallOrderForMybatis;
import com.example.learn.springboo.data.repository.impl.mapper.CustomerMapper;
import com.example.learn.springboo.data.repository.impl.mapper.MallOrderMapper;
import com.example.learn.springboo.data.repository.impl.repository.CustomerRepository;
import com.example.learn.springboo.data.repository.impl.template.CustomerDaoWithJdbcTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.*;

import java.time.LocalDateTime;
import java.util.*;

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
	@Autowired
	MallOrderMapper mallOrderMapper;

	@Autowired
	@Qualifier("&mallOrderMapperFactoryBean")
	MapperFactoryBean mallOrderMapperFactoryBean;

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;


	@Autowired
	PersonRepository personRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public void run(ApplicationArguments args) throws Exception {


		myRepository.save();
		Customer customer = myRepository.query("XF00003");
		log.info("[Use jdbcTemplate ]query customer {customerCode=XF00003}  ,customer :{} ",customer);

		Customer insertCustomer = new Customer("XF00004", "娃哈哈集团", "0571-123456", 1, LocalDateTime.now().plusHours(2));
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

		Page<Customer> page = customerRepository.findAll(PageRequest.of(0,3,Sort.by(Sort.Order.asc("createTime"))));
		log.info("[Use jdbc repository] customerRepository.findAll page 0,size:2 : {} ",objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(page));

		Iterable<Customer> customerIterable = customerRepository.findAll(Sort.by(Sort.Order.asc("createTime")));
		log.info("[Use jdbc repository] customerRepository.findAll : {} ",objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerIterable));

		int updateFlag = customerMapper.update("modify-666","XF00003");
		log.info("[Use mybatis ] update customer : {} ",updateFlag);
		CustomerForMybatis customerQueryByMybatis = customerMapper.findByCustomerCode("XF00003");
		log.info("[Use mybatis ] query customer : {} ",customerQueryByMybatis);

		List<CustomerForMybatis> customerForMybatisList = customerMapper.queryAll();
		log.info("[Use mybatis ] queryAll customer : {} ",customerForMybatisList);

		CustomerForMybatis customerForMybatisForInsert = new CustomerForMybatis();
		customerForMybatisForInsert.setCreateTime(LocalDateTime.now().plusHours(1));
		customerForMybatisForInsert.setCustomerCode("XF00005");
		customerForMybatisForInsert.setCustomerName("腾讯科技");
		customerForMybatisForInsert.setCustomerPhone("0772-32312222");
		customerForMybatisForInsert.setIsMember(1);
		int saveFlag = customerMapper.insert(customerForMybatisForInsert);
		log.info("[Use mybatis ] insert customer : {} ",saveFlag);
		List<MallOrderForMybatis> mallOrders = mallOrderMapper.queryByCustomerCode("XF00001");
		log.info("[Use mybatis ] query mallOrders : {} ",mallOrders);

		clearRedis();

		redisTemplate.opsForValue().set("env","dev");
		Object env = redisTemplate.opsForValue().get("env");
		log.info("opsForValue env:{}",env);

		stringRedisTemplate.opsForValue().set("name","kim");
		String name = stringRedisTemplate.opsForValue().get("name");
		log.info("opsForValue name:{}",name);
		Map<String,Object> map = new HashMap<>();
		map.put("name","kim");
		map.put("height","173");
		stringRedisTemplate.opsForHash().putAll("myself",map);
		List<Object> mapValues = stringRedisTemplate.opsForHash().multiGet("myself",List.of("name","height"));
		log.info("opsForHash mapValues:{}",mapValues);
		log.info("stringRedisTemplate.opsForHash().scan ......");
		try(Cursor<Map.Entry<Object, Object>> cursor = stringRedisTemplate.opsForHash().scan("myself", ScanOptions.scanOptions().count(10).build())){
			while(cursor.hasNext()){
				Map.Entry<Object, Object> entry = cursor.next();
				log.info(entry.getKey()+":"+entry.getValue());
			}
		}

		stringRedisTemplate.opsForList().rightPushAll("rank","kim","tom","gates","jack","clinton");
		stringRedisTemplate.opsForList().leftPush("rank","gates","lily");
		List<String> rank = stringRedisTemplate.opsForList().range("rank",0,stringRedisTemplate.opsForList().size("rank"));
		//stringRedisTemplate.opsForList().move(ListOperations.MoveFrom.fromHead("rank"), ListOperations.MoveTo.toHead("rank"));
		//stringRedisTemplate.opsForList().move(ListOperations.MoveFrom.fromHead("rank"), ListOperations.MoveTo.toHead("rank_bak"));
		List<String> rank_bak = stringRedisTemplate.opsForList().range("rank_bak",0,stringRedisTemplate.opsForList().size("rank_bak"));
		log.info("stringRedisTemplate.opsForList().range rank: {}",rank);
		log.info("stringRedisTemplate.opsForList().range rank_bak: {}",rank_bak);

		Person person = new Person();
		person.setFirstname("allan");
		person.setLastname("kim");
		person.setAge(30);
		Person.Address address = new Person.Address("hangzhou", "shu guang road");
		person.setAddress(address);
		personRepository.save(person);

		person.setFirstname("jack");
		person.setLastname("ma");
		address = new Person.Address("hangzhou", "alibaba");
		person.setAddress(address);
		person.setAge(50);
		person.setId(null);
		personRepository.save(person);

		person.setFirstname("bill");
		person.setLastname("gates");
		address = new Person.Address("new york", "曼哈登");
		person.setAddress(address);
		person.setAge(40);
		person.setId(null);
		personRepository.save(person);

		Optional<Person> personFromRedis = personRepository.findById(person.getId());
		log.info("[Use redis repository ] personRepository.findById:{}",personFromRedis.orElseGet(()->new Person()));
		log.info("personRepository instanceof KeyValueRepository：{}",personRepository instanceof KeyValueRepository);
		Iterable<Person>  personsWithoutSort = personRepository.findAll();
		log.info("personRepository.findAll :{} ",objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(personsWithoutSort));
		Iterable<Person>  persons = personRepository.findAll(Sort.by(Sort.Direction.ASC,"age"));
		log.info("personRepository.findAll sort by age asc :{} ",objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(persons));
		log.info("personRepository.findAll sort by age desc :{} ",objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(personRepository.findAll(Sort.by(Sort.Direction.DESC,"age"))));
		Pageable pageable = PageRequest.of(0,2,Sort.by(Sort.Direction.DESC,"age"));
		Page<Person> personPage = personRepository.findAll(pageable);
		log.info("personRepository.findAll by pageable [page=0,size=2]:{}",objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(personPage));


/*
探索interceptor chain构造与使用：
		AnotherInterceptor test = new AnotherInterceptor();
		InterceptorChain chain = new InterceptorChain();
		chain.addInterceptor(test);
		Object  object = chain.pluginAll(customerRepository);
		CustomerRepository cr = ((CustomerRepository)object);
		boolean updateFlag2 = cr.update("XF00003","modify-12345");
		log.info("[Use jdbc repository] update customer :{} ",updateFlag2);*/

	}

	private void clearRedis(){
		log.info("clearRedis start............");
		Set<String> keys = stringRedisTemplate.keys("person*");
		keys.addAll(List.of("rank","rank_bak","myself"));
		long count = stringRedisTemplate.delete(keys);
		log.info("stringRedisTemplate.delete keys count :{}",count);
		log.info("clearRedis end..............");
	}
}
