package com.example.learn.springboot.storage;

import com.example.learn.springboot.storage.factory.StorageFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliyunStorage extends Storage {
    @Override
    public void save(String content) {
        log.info("AliyunStorage save content : {}",content);
    }
    public static class Factory implements StorageFactory {
        @Override
        public Storage getStorage(ClassLoader classLoader) {
            return new AliyunStorage();
        }
    }
}
