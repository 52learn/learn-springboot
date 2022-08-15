package com.example.learn.springboot.sms.autoconfigure;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Conditional(OnEnabledSmsProviderCondition.class)
public @interface ConditionalOnEnabledSmsProvider {
	String value();
}