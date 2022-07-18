package com.example.learn.springboot.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameUniqueValidator implements ConstraintValidator<UserNameUnique,String> {
    private UserRepository userRepository;
    public UserNameUniqueValidator(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value) && !userRepository.findByUserName(value).isPresent();
    }
}
