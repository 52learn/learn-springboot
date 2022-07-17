package com.example.learn.springboot.diagnostics;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;

public class ProjectConstraintViolationApplicationRunner  implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        ClassPathResource myProperties = new ClassPathResource("/META-INF/my.properties3",this.getClass());
        if(!myProperties.exists()){
            throw new ProjectConstraintViolationException(myProperties.getPath()+" not exist ");
        }
    }
}
