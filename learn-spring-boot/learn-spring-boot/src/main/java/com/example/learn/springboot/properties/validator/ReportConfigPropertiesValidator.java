package com.example.learn.springboot.properties.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ReportConfigPropertiesValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ReportConfigProperties.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReportConfigProperties reportConfigProperties = (ReportConfigProperties)target;
        if(reportConfigProperties.isSendByEmail()){
            ValidationUtils.rejectIfEmpty(errors,"emailSubject",String.valueOf(5000),"Email subject is required");
            ValidationUtils.rejectIfEmpty(errors,"recipient",String.valueOf(5000),"Email recipient is required");
        }
    }
}
