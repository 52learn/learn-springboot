package com.example.learn.springboot.i18n;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class I18nMessageAutoConfiguration {

    @Bean
    public I18nMessagePrinter i18nMessagePrinter(){
        return new I18nMessagePrinter();
    }
}
