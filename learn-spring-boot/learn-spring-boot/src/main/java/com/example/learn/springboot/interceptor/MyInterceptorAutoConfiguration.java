package com.example.learn.springboot.interceptor;

import com.example.learn.springboot.web.WebController;
import org.aspectj.lang.annotation.Aspect;
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
@Aspect
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

    //@Bean
    Advisor controllerAdvisor(CustomizableTraceInterceptor customizableTraceInterceptor){
        //AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        //pointcut.setExpression("execution(public * com.example.learn.springboot.web.WebController.*(..))");
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        Method[] methods = WebController.class.getMethods();
        Arrays.stream(methods).forEach(method -> {
            pointcut.addMethodName(method.getName());
        });
        return new DefaultPointcutAdvisor(pointcut,customizableTraceInterceptor);
    }
    @Bean
    Advisor shoppingCartServiceAdvisor(TracingInterceptor tracingInterceptor){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * com.example.learn.springboot.interceptor.ShoppingCartService.*(..))");
        return new DefaultPointcutAdvisor(pointcut, tracingInterceptor);
    }
    @Bean
    public Advisor controllerAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        //pointcut.setExpression("execution(public * com.example.learn.springboot.web.WebController+.*(..))");
        //pointcut.setExpression("execution(public * (@org.springframework.web.bind.annotation.GetMapping com.example.learn.springboot.web.WebController..*).*(..))");
        pointcut.setExpression("execution(* com.example.learn.springboot.web.WebController.getPerson(..))");
        return new DefaultPointcutAdvisor(pointcut, tracingInterceptor());
    }
}
