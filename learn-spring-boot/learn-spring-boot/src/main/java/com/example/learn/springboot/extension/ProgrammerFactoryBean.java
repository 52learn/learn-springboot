package com.example.learn.springboot.extension;

import com.example.learn.springboot.extension.po.Programmer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
@Slf4j
public class ProgrammerFactoryBean implements FactoryBean<Programmer> {
    @Override
    public Programmer getObject() throws Exception {
        Programmer p = new Programmer();
        p.setName("martin");
        log.info("FactoryBean.getObject() programmer :{}",p);
        return p;
    }

    @Override
    public Class<?> getObjectType() {
        return Programmer.class;
    }
}
