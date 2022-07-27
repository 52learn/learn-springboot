package com.example.learn.springboo.data.repository.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@Slf4j
@Intercepts(value = {@Signature(type = Executor.class,method = "update",args = {MappedStatement.class ,Object.class})})
public class ModifyCountAutoIncrementInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = ((MappedStatement)invocation.getArgs()[0]);
        SqlSource sqlSource = ms.getSqlSource();
        if(sqlSource instanceof RawSqlSource){
            RawSqlSource rss = (RawSqlSource)sqlSource;
            Field sqlSourceField = ReflectionUtils.findField(RawSqlSource.class, "sqlSource");
            ReflectionUtils.makeAccessible(sqlSourceField);
            SqlSource ss = (SqlSource)ReflectionUtils.getField(sqlSourceField,rss);
            if(ss instanceof StaticSqlSource){
                StaticSqlSource sss = (StaticSqlSource)ss;
                Field sqlField = ReflectionUtils.findField(StaticSqlSource.class, "sql");
                ReflectionUtils.makeAccessible(sqlField);
                String sql = (String) ReflectionUtils.getField(sqlField,sss);
                sql = sql.toLowerCase().trim();
                // only update clause
                if(SqlCommandType.UPDATE.equals(ms.getSqlCommandType())){
                    int whereIndex = sql.indexOf("where");
                    if(whereIndex!=-1){
                        sql = sql.substring(0,whereIndex)+",modifier_count=modifier_count+1 "+sql.substring(whereIndex);
                    }else{
                        sql = sql +",modifier_count=modifier_count+1 ";
                    }
                    log.info("[mybatis interceptor: ] change update sql : {}",sql);
                    ReflectionUtils.setField(sqlField,sss,sql);
                }
            }
        }
        Object returnObject =  invocation.proceed();
        return returnObject;
    }

}
