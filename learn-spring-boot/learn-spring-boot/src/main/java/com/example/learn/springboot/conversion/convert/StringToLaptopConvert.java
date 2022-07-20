package com.example.learn.springboot.conversion.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;

/**
 * format 1:
 * {"brand":"thinkpad","inch":14}
 *
 * format 2:
 * mac,15
 */
@Slf4j
public class StringToLaptopConvert implements Converter<String,Laptop> {
    ObjectMapper objectMapper;
    public StringToLaptopConvert(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    @Override
    public Laptop convert(String source) {
        try {
            JsonParser jsonParser = objectMapper.createParser(source);
            //Laptop laptop = objectMapper.readValue(jsonParser, new TypeReference<Laptop>() {});
            Laptop laptop = objectMapper.readValue(jsonParser,Laptop.class);
            return laptop;
        } catch (IOException e) {
            log.warn(String.format("objectMapper.createParser error :%s , go on parse ",source));
            String[] fileds = source.split(",");
            Laptop laptop = new Laptop();
            laptop.setBrand(fileds[0]);
            laptop.setInch(Double.parseDouble(fileds[1]));
            return laptop;
        }
    }
}
