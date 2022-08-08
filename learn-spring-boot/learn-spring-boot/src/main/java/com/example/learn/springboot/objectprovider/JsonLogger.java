package com.example.learn.springboot.objectprovider;

import lombok.extern.slf4j.Slf4j;

public class JsonLogger implements LogService {
  @Override
  public void log(String data) {
    System.out.printf("{\"log\": { \"message\": \"%s\", \"timestamp\": %d } }", data, System.currentTimeMillis());
  }
}