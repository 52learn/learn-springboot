package com.example.learn.springboot.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ToString
@ConfigurationProperties("customer")
@Validated
@Getter
@Setter
public class CustomerProperties {
    @NotNull
    private String name;
    @NotNull(message = "客户有效期(按天计)不能为空")
    private Duration validPeriod;
    @Valid
    private Contract contract;
    @Getter
    @Setter
    @ToString
    public static class Contract{
        @NotNull
        private String name;
        @NotEmpty
        private String phone;
    }
}
