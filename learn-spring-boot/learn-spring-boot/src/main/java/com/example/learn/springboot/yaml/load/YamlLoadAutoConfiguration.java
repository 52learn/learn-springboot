package com.example.learn.springboot.yaml.load;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@AutoConfiguration
public class YamlLoadAutoConfiguration {
    @Bean
    public YamlMapFactoryBean yamlMap() {
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("META-INF/load-yaml.yaml"));
        return yamlMapFactoryBean;
    }

    @Bean
    public YamlPropertiesFactoryBean yamlProperties(){
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("META-INF/load-yaml.yaml"));
        return yamlPropertiesFactoryBean;
    }

}
