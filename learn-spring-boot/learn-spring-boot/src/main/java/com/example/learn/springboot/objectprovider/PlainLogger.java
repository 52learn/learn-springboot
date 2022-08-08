package com.example.learn.springboot.objectprovider;

import lombok.extern.slf4j.Slf4j;

public class PlainLogger implements LogService {
  @Override
  public void log(String data) {
    System.out.printf("Data [%s] at %d%n", data, System.currentTimeMillis());
  }
}