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
2. 使用@Profile
com.example.learn.springboot.profile.DevAutoConfiguration  
com.example.learn.springboot.profile.UatAutoConfiguration  


