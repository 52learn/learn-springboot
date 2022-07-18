package com.example.learn.springboot.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Slf4j
public class I18nMessagePrinter {
    @Autowired
    private MessageSource messageSource;

    public void print(){
        log.info("print message with Locale.ENGLISH : {}",messageSource.getMessage("introduction.content",new String[]{"name","age"}, Locale.ENGLISH));
        log.info("print message with Locale.ZH : {}",messageSource.getMessage("introduction.content",new String[]{"姓名","年龄"}, Locale.CHINESE));
        log.info("print message  key not exist : {}",messageSource.getMessage("warnning.content", null,Locale.ENGLISH));
    }
}
