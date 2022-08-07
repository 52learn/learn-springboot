package com.example.learn.springboo.data.nosql.redis;

import com.example.learn.springboo.data.nosql.redis.repository.Person;
import com.example.learn.springboo.data.nosql.redis.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
@Slf4j
public class RedisOperatioinExample {
    RedisTemplate redisTemplate;
    PersonRepository personRepository;
    ObjectMapper objectMapper;
    StringRedisTemplate stringRedisTemplate;
    public RedisOperatioinExample(RedisTemplate redisTemplate,StringRedisTemplate stringRedisTemplate,PersonRepository personRepository,
        ObjectMapper objectMapper){
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.personRepository = personRepository;
        this.objectMapper = objectMapper;
    }

    public void apiInvoke() throws Exception{
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

        Person.Address address =Person.Address.builder()
                .city("hangzhou")
                .street("nanyang road")
                .build();
        Person person = Person.builder()
                .firstname("allan")
                .lastname("kim")
                .age(30)
                .address(address)
                .build();
        personRepository.save(person);

        person.setFirstname("jack");
        person.setLastname("ma");
        address = new Person.Address("hangzhou", "alibaba");
        person.setAddress(address);
        person.setAge(50);
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
