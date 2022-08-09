package com.example.learn.springboot.objectprovider;

public class PlainLogger implements LogService {
  @Override
  public void log(String data) {
    System.out.println(String.format("Data [%s] at %d%n", data, System.currentTimeMillis()));
  }
}