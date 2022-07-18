package com.example.learn.springboot.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNameUniqueValidator.class)
public @interface UserNameUnique {
    String message() default "{com.example.learn.springboot.validation.UserNameUnique.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}