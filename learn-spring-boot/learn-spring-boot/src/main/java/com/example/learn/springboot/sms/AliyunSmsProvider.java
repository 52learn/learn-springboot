package com.example.learn.springboot.sms;

public class AliyunSmsProvider implements SmsProvider {
    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
