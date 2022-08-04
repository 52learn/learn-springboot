package com.example.learn.springboo.data.repository;

import com.example.learn.springboo.data.repository.entity.Customer;
import com.example.learn.springboo.data.repository.entity.CustomerForMybatis;
import com.example.learn.springboo.data.repository.entity.MallOrderForMybatis;
import com.example.learn.springboo.data.repository.impl.mapper.CustomerMapper;
import com.example.learn.springboo.data.repository.impl.mapper.MallOrderMapper;
import com.example.learn.springboo.data.repository.impl.repository.CustomerRepository;
import com.example.learn.springboo.data.repository.impl.template.CustomerDaoWithJdbcTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
public class MySqlOperationExample {
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
    ObjectMapper objectMapper;
    public void apiInvoke() throws Exception{

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
}
