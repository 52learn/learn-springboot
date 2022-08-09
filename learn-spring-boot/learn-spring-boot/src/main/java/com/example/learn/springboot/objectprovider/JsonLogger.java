package com.example.learn.springboot.objectprovider;

public class JsonLogger implements LogService {
  @Override
  public void log(String data) {
    System.out.println(String.format("{\"log\": { \"message\": \"%s\", \"timestamp\": %d } }", data, System.currentTimeMillis()));
  }
}