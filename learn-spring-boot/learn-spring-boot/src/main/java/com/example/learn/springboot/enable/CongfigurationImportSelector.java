package com.example.learn.springboot.enable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CongfigurationImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //return new String[]{};
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(
            EnableConfigurationImportSelector.class.getName(), false));
        Class[] configurations = attributes.getClassArray("configurations");
        List<String> classNames = new ArrayList<>();
        for(Class configurationClass:configurations){
            log.info(" configurationClass, CanonicalName:{} ,PackageName:{}, SimpleName:{}, SimpleName:{}, TypeName:{}, Name:{}",
                configurationClass.getCanonicalName(),
                configurationClass.getPackageName(),
                configurationClass.getSimpleName(),
                configurationClass.getTypeName(),
                configurationClass.getName());
            if(configurationClass.getCanonicalName()!=null){
                classNames.add(configurationClass.getCanonicalName());
            }
        }
        return classNames.toArray(new String[classNames.size()]);
    }

}
