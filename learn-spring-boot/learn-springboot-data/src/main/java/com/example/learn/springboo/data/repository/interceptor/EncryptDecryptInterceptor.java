package com.example.learn.springboo.data.repository.interceptor;

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
 * 由于无法获取@Mapper接口类的方法信息，只能通过获取更新字段参数信息来区分；
 *
 */
@Slf4j
@Intercepts(value = {@Signature(type = Executor.class,method = "update",args = {MappedStatement.class ,Object.class})})
public class EncryptDecryptInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        MappedStatement ms = (MappedStatement)invocation.getArgs()[0];
        Object arg1 = invocation.getArgs()[1];
        SqlSource sqlSource = ms.getSqlSource();
        if(sqlSource instanceof RawSqlSource){
            if(SqlCommandType.UPDATE.equals(ms.getSqlCommandType()) || SqlCommandType.INSERT.equals(ms.getSqlCommandType())){

            }
        }

        Object returnObject =  invocation.proceed();
        return returnObject;
    }
}
