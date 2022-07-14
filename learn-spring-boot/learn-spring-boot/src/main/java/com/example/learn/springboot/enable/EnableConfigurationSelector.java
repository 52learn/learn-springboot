package com.example.learn.springboot.enable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(CongfigurationSelector.class)
public @interface EnableConfigurationSelector {
    Class<?>[] configurations() default {};
}
