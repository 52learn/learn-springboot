package com.example.learn.springboot.aop;

import com.example.learn.springboot.web.WebController;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
@EnableAspectJAutoProxy
@AutoConfiguration
public class MyInterceptorAutoConfiguration {
    @Bean
    ShoppingCartService shoppingCartService(){
        return new ShoppingCartServiceImpl();
    }
    @Bean
    TracingInterceptor tracingInterceptor(){
        return new TracingInterceptor();
    }

    @Bean
    CustomizableTraceInterceptor customizableTraceInterceptor(){
        CustomizableTraceInterceptor customizableTraceInterceptor = new CustomizableTraceInterceptor();
        customizableTraceInterceptor.setUseDynamicLogger(true);
        customizableTraceInterceptor.setEnterMessage("Entering $[methodName]($[arguments])");
        customizableTraceInterceptor.setExitMessage("Leaving $[methodName](),returned $[returnValue]");
        return customizableTraceInterceptor;
    }


    @Bean
    Advisor shoppingCartServiceAdvisor(TracingInterceptor tracingInterceptor){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * com.example.learn.springboot.aop.ShoppingCartService.*(..))");
        return new DefaultPointcutAdvisor(pointcut, tracingInterceptor);
    }

    @Bean
    Advisor controllerAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.example.learn.springboot.web.WebController.getPerson(..))");
        return new DefaultPointcutAdvisor(pointcut, tracingInterceptor());
    }

    @Bean
    LoggingAspect loggingAspect(){
        return new LoggingAspect();
    }

}
