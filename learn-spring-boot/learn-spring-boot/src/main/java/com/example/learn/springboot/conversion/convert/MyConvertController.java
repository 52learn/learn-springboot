package com.example.learn.springboot.conversion.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MyConvertController {
    @PostMapping("/convert")
    public String convert(@RequestBody Laptop laptop){
        log.info(" laptop  : {}",laptop);
        return laptop.toString();
    }

    @GetMapping("/convert/param")
    public String convertFromRequestParam(@RequestParam("laptop") Laptop laptop){
        log.info(" laptop  : {}",laptop);
        return laptop.toString();
    }
}
