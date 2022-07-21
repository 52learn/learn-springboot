package com.example.learn.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesHttpMessageConverter extends AbstractHttpMessageConverter<Properties> {
    StringHttpMessageConverter stringHttpMessageConverter;
    ConversionService conversionService;
    public PropertiesHttpMessageConverter(StringHttpMessageConverter stringHttpMessageConverter,ConversionService conversionService,MediaType supportedMediaType){
        super(supportedMediaType);
        this.stringHttpMessageConverter = stringHttpMessageConverter;
        this.conversionService = conversionService;
    }
    @Override
    protected boolean supports(Class clazz) {
        return Object.class.isAssignableFrom(clazz);
    }

    @Override
    protected Properties readInternal(Class clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException {
        Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
        String content = StreamUtils.copyToString(inputMessage.getBody(), charset);
        /*InputStreamResource isr = new InputStreamResource(inputMessage.getBody());
        Properties properties = PropertiesLoaderUtils.loadProperties(isr);*/
        Properties properties = new Properties();
        properties.load(new StringReader(content));
        return properties;
    }

    @Override
    protected void writeInternal(Properties properties, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {
        String value = this.conversionService.convert(properties, String.class);
        if (value != null) {
            this.stringHttpMessageConverter.write(value,null, outputMessage);
        }

    }

    private Charset getContentTypeCharset(@Nullable MediaType contentType) {
        if (contentType != null) {
            Charset charset = contentType.getCharset();
            if (charset != null) {
                return charset;
            }
            else if (contentType.isCompatibleWith(MediaType.TEXT_PLAIN)) {
                // Matching to AbstractJackson2HttpMessageConverter#DEFAULT_CHARSET
                return StandardCharsets.UTF_8;
            }
        }
        Charset charset = getDefaultCharset();
        Assert.state(charset != null, "No default charset");
        return charset;
    }

}
