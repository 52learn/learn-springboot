package com.example.learn.springboot.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
@Slf4j
public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("method "+invocation.getMethod()+" is called on "+invocation.getThis()+" with args "+invocation.getArguments());
        Object ret=invocation.proceed();
        log.info("method "+invocation.getMethod()+" returns "+ret);
        return ret;
    }
}
