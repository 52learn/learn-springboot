package com.example.learn.springboot.properties.validator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Getter
@Setter
@ConfigurationProperties("report")
public class ReportConfigProperties implements Validator{
    private boolean sendByEmail;
    private String emailSubject;
    private String recipient;


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
