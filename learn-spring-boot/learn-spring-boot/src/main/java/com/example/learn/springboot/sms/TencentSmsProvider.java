package com.example.learn.springboot.sms;

public class TencentSmsProvider implements SmsProvider {
    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
