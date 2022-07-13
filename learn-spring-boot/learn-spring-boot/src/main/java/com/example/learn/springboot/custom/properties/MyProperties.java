package com.example.learn.springboot.custom.properties;

import org.springframework.boot.info.InfoProperties;

import java.util.Properties;

public class MyProperties extends InfoProperties {
    /**
     * Create an instance with the specified entries.
     *
     * @param entries the information to expose
     */
    public MyProperties(Properties entries) {
        super(entries);
    }


}
