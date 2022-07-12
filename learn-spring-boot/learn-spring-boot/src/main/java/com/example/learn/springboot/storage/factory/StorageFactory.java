package com.example.learn.springboot.storage.factory;

import com.example.learn.springboot.storage.Storage;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.function.Function;

public interface StorageFactory {
    Storage getStorage(ClassLoader classLoader);

    static StorageFactory fromSpringFactories(){
        Function<ClassLoader, List<StorageFactory>> function = classLoader -> SpringFactoriesLoader.loadFactories(StorageFactory.class, classLoader);
        return new DelegatingStorageFactory(function);
    }
}
