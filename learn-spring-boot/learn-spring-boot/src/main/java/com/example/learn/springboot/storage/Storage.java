package com.example.learn.springboot.storage;

import com.example.learn.springboot.storage.factory.StorageFactory;
import org.springframework.util.Assert;


public abstract class Storage {
    private String name;

    public abstract void save(String content);
    private static final StorageFactory STORAGE_FACTORY = StorageFactory.fromSpringFactories();

    static Storage get(ClassLoader classLoader) {
        Storage storage = STORAGE_FACTORY.getStorage(classLoader);
        Assert.state(storage != null, "No suitable storage located");
        return storage;
    }
}
