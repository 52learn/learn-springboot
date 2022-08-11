package com.example.learn.springboot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;

@Slf4j
@Aspect
public class LoggingAspect{
    @Pointcut("execution(* com.example.learn.springboot.aop.ShoppingCartService.totalMoney(..))")
    public void loggingPointcut() {
    }

    /**
     * @see @link https://solidsyntax.be/2013/12/10/access-concrete-class-spring-proxy/
     * @param joinpoint
     */
    @Before("loggingPointcut()")
    public void logBefore(JoinPoint joinpoint){
        Class joinPointThisTargetClass = AopUtils.getTargetClass(joinpoint.getThis());
        Class joinPointTargetTargetClass = AopUtils.getTargetClass(joinpoint.getTarget());
        Class thisUltimateTargetClass = AopProxyUtils.ultimateTargetClass(joinpoint.getThis());
        Class targetUltimateTargetClass = AopProxyUtils.ultimateTargetClass(joinpoint.getTarget());
        log.info(">> LoggingAspect before advice:.....joinPointThisTargetClass:{},"
            + "joinPointTargetTargetClass:{},"
            + "thisUltimateTargetClass:{},"
            + "targetUltimateTargetClass:{} ",
            joinPointThisTargetClass.getCanonicalName(),
            joinPointTargetTargetClass.getCanonicalName(),
            thisUltimateTargetClass.getCanonicalName(),
            targetUltimateTargetClass.getCanonicalName());
    }

    @After("loggingPointcut()")
    public void logAfter(JoinPoint joinpoint){
        log.info(">> LoggingAspect after advice.......");
    }

    @Around("loggingPointcut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long end = System.currentTimeMillis();
        String methodName = proceedingJoinPoint.getSignature().getName();
        log.info(">> LoggingAspect method:{},consume:{} mills",methodName,(end-start));
    }
}