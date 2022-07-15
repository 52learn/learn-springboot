# Feature List
## Customize Conditional 
1. Define OnEnabledXXXCondition class
com.example.learn.springboot.autoconfigure.OnEnabledSmsProviderCondition

2. Define ConditionalOnEnabledXXX class
com.example.learn.springboot.autoconfigure.ConditionalOnEnabledSmsProvider

3. Define XXXAutoConfiguration class
com.example.learn.springboot.autoconfigure.SmsProviderAutoConfiguration

4. Config spring.factories

5. Config application.yaml 

## Use @Profile
1. 设置spring.profiles.active
- By startup with parameter  
```
java -jar xxxx.jar  --spring.profiles.active=dev
```
- Config in application.yaml 
```
spring:
  profiles:
    active: dev
```
2. Use @Profile
com.example.learn.springboot.profile.autoconfigure.DevAutoConfiguration  
com.example.learn.springboot.profile.autoconfigure.UatAutoConfiguration  

## How to apply Factory Pattern with spring.factories

1. define storage factories 
- com.example.learn.springboot.storage.factory.StorageFactory
- com.example.learn.springboot.storage.LocalFileStorage.Factory
- com.example.learn.springboot.storage.AliyunStorage.Factory
- com.example.learn.springboot.storage.factory.DelegatingStorageFactory

2. define Storage abstract class and concrete class
- com.example.learn.springboot.storage.Storage
- com.example.learn.springboot.storage.AliyunStorage
- com.example.learn.springboot.storage.LocalFileStorage

3. define ApplicationListener class
com.example.learn.springboot.storage.StorageApplicationListener

4. config spring.factories:
```
# Application Listeners
org.springframework.context.ApplicationListener=\
com.example.learn.springboot.storage.StorageApplicationListener

com.example.learn.springboot.storage.factory.StorageFactory=\
com.example.learn.springboot.storage.AliyunStorage.Factory,\
com.example.learn.springboot.storage.LocalFileStorage.Factory\
```
## Use BeanPostProcessor 
- com.example.learn.springboot.bean.processor.autoconfigure.BeanPostProcessorAutoConfiguration
- com.example.learn.springboot.bean.processor.MyBeanPostProcessor

## @EnableXXX
- @EnableSomeBeanConfiguration
- @EnableConfigurationSelector
- @EnableSomeBeanRegistrar

Reference：  
@Enable**注解实现原理与实例  
https://blog.csdn.net/w47_csdn/article/details/86611237  

动态注册bean，Spring官方套路：使用ImportBeanDefinitionRegistrar  
https://zhuanlan.zhihu.com/p/30123517

