package com.example.learn.springboot.storage.factory;

import com.example.learn.springboot.storage.Storage;

import java.util.List;
import java.util.function.Function;

/**
 * 委托存储Factory的作用：
 * 能够从所有StorageFactory中选择一个创建Storage
 */
public class DelegatingStorageFactory implements StorageFactory {
    private final Function<ClassLoader, List<StorageFactory>> delegates;

    DelegatingStorageFactory(Function<ClassLoader, List<StorageFactory>> delegates){
        this.delegates = delegates;
    }

    @Override
    public Storage getStorage(ClassLoader classLoader) {
        List<StorageFactory> delegates = (this.delegates!=null) ? this.delegates.apply(classLoader) : null;
        if(delegates!=null){
            for(StorageFactory delegate:delegates){
                Storage storage = delegate.getStorage(classLoader);
                if(storage!=null){
                    return storage;
                }
            }
        }
        return null;
    }
}
