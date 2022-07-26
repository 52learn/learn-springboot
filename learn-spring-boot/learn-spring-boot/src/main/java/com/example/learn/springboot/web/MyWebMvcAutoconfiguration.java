package com.example.learn.springboot.web;

import com.example.learn.springboot.web.interceptor.MyHandlerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@AutoConfiguration(after = HttpMessageConvertersAutoConfiguration.class)
public class MyWebMvcAutoconfiguration {
    @Bean
    public HttpMessageConverter<Object> xmlHttpMessageConverter() {
        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
        xmlConverter.setMarshaller(xstreamMarshaller);
        xmlConverter.setUnmarshaller(xstreamMarshaller);
        return xmlConverter;
    }

    @Bean
    PropertiesHttpMessageConverter myHttpMessageConverter(StringHttpMessageConverter stringHttpMessageConverter,
        ConversionService conversionService){
        return new PropertiesHttpMessageConverter(stringHttpMessageConverter,conversionService,MediaType.TEXT_PLAIN);
    }
    @Bean
    WebController webController(){
        return new WebController();
    }



    @Bean
    MyWebMvcConfigurer myWebMvcConfigurer(){
        return new MyWebMvcConfigurer();
    }
}
