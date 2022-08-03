package com.example.learn.springboo.data.repository.interceptor;

import com.example.learn.springboo.data.repository.impl.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

import java.lang.reflect.Method;

/**
 *
 */
@Slf4j
@Intercepts(value = {@Signature(type = CustomerRepository.class,method = "update",args = {String.class ,String.class})})
public class AnotherInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();

        Object returnObject =  invocation.proceed();
        return returnObject;
    }
}
