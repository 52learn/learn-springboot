package com.example.learn.springboot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
@Slf4j
public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("args :{}",invocation.getArguments());
        Object ret=invocation.proceed();
        log.info("returns :{}",ret);
        return ret;
    }
}
