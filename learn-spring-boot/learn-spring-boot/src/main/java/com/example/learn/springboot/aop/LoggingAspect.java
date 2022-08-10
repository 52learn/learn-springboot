package com.example.learn.springboot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class LoggingAspect{
    @Pointcut("execution(* com.vd.canary.obmp.arm.model.process.condition.Condition.execute(..))")
    public void loggingPointcut() {
    }

    @Before("loggingPointcut()")
    public void log(JoinPoint joinpoint){
        String beanName = AspectUtil.getTargetBeanName(joinpoint);
        log.info(">> LoggingAspect.....:"+beanName);
    }
}