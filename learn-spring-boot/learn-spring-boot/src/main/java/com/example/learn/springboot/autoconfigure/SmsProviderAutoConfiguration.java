package com.example.learn.springboot.autoconfigure;

import com.example.learn.springboot.sms.AliyunSmsProvider;
import com.example.learn.springboot.sms.SmsProvider;
import com.example.learn.springboot.sms.TencentSmsProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class SmsProviderAutoConfiguration {
    @Bean
    @ConditionalOnEnabledSmsProvider(value = "aliyun")
    public SmsProvider aliyunSmsProvider() {
        return new AliyunSmsProvider();
    }

    @Bean
    @ConditionalOnEnabledSmsProvider(value = "tencent")
    public SmsProvider tencentSmsProvider() {
        return new TencentSmsProvider();
    }
}
