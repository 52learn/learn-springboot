package com.example.learn.springboot.storage;

import com.example.learn.springboot.storage.factory.StorageFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalFileStorage extends Storage {

    public LocalFileStorage(){
        log.info(" LocalFileStorage instantiation....");
        System.out.println("LocalFileStorage instantiation....");
    }
    @Override
    public void save(String content) {
        log.info("LocalFileStorage save content : {}",content);
    }

    public static class Factory implements StorageFactory {
        @Override
        public Storage getStorage(ClassLoader classLoader) {
            return new LocalFileStorage();
        }
    }
}
