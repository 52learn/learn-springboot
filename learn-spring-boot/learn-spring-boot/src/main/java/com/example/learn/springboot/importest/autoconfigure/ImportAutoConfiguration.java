package com.example.learn.springboot.importest.autoconfigure;

import com.example.learn.springboot.importest.OrderModuleConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(OrderModuleConfiguration.class)
public class ImportAutoConfiguration {

}
