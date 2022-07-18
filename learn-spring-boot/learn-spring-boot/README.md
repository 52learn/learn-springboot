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

## Custom Diagnostics Exception
once app startup ,it should check the app's construct, if violation the project constraint , then throw ProjectConstraintViolationException ,  startup fails 

- com.example.learn.springboot.diagnostics.DiagnosticsAutoConfiguration
- com.example.learn.springboot.diagnostics.ProjectConstraintViolationApplicationRunner
- com.example.learn.springboot.diagnostics.ProjectConstraintViolationException
- com.example.learn.springboot.diagnostics.ProjectConstraintViolationFailureAnalyzer
- spring.factories
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.learn.springboot.diagnostics.DiagnosticsAutoConfiguration 
# Failure Analyzers
org.springframework.boot.diagnostics.FailureAnalyzer=\
com.example.learn.springboot.diagnostics.ProjectConstraintViolationFailureAnalyzer
```

## Custom FailureAnalyzer
- com.example.learn.springboot.diagnostics.MyFailureAnalyzers
- com.example.learn.springboot.diagnostics.MyLoggingFailureAnalysisReporter
- spring.factories
```
org.springframework.boot.SpringBootExceptionReporter=\
com.example.learn.springboot.diagnostics.MyFailureAnalyzers  

# Failure Analysis Reporters
org.springframework.boot.diagnostics.FailureAnalysisReporter=\
com.example.learn.springboot.diagnostics.MyLoggingFailureAnalysisReporter
```

## change  spring-jcl log level in appliction.yaml
spring boot framework use spring-jcl for logging.  how to change the log level?

eg : change the  org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter.logger level to log the debug info.
application.yaml :
```
logging:
  level:
    org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter: debug
```

## Directly Loading YAML
- com.example.learn.springboot.yaml.load.YamlLoadAutoConfiguration.yamlMap
- com.example.learn.springboot.yaml.load.YamlLoadAutoConfiguration.yamlProperties

## Directly Loading YAML as property source 
- com.example.learn.springboot.yaml.load.YamlLoadApplicationListener

## Custom Validator 
- com.example.learn.springboot.validation.UserNameUniqueValidator
- com.example.learn.springboot.validation.UserNameUnique
- com.example.learn.springboot.validation.ValidationAutoConfiguration

```
curl -H "Content-Type:application/json" -X POST -d '{"userName": "kim"}' http://127.0.0.1:8080/regist
```
referenece:  
http://dolszewski.com/spring/custom-validation-annotation-in-spring/ 

## Learn knowledge
### 获取类名中泛型类型
org.springframework.boot.diagnostics.AbstractFailureAnalyzer.getCauseType
```
/**
 * Return the cause type being handled by the analyzer. By default the class generic
 * is used.
 * @return the cause type
 */
@SuppressWarnings("unchecked")
protected Class<? extends T> getCauseType() {
    return (Class<? extends T>) ResolvableType.forClass(AbstractFailureAnalyzer.class, getClass()).resolveGeneric();
}
```
### 在Throwable中获取异常Cause
```
@SuppressWarnings("unchecked")
protected final <E extends Throwable> E findCause(Throwable failure, Class<E> type) {
    while (failure != null) {
        if (type.isInstance(failure)) {
            return (E) failure;
        }
        failure = failure.getCause();
    }
    return null;
}
```

### spring boot default search config properties/yaml locations 
org.springframework.boot.context.config.ConfigDataEnvironment.DEFAULT_SEARCH_LOCATIONS
```
static {
    List<ConfigDataLocation> locations = new ArrayList<>();
    locations.add(ConfigDataLocation.of("optional:classpath:/;optional:classpath:/config/"));
    locations.add(ConfigDataLocation.of("optional:file:./;optional:file:./config/;optional:file:./config/*/"));
    DEFAULT_SEARCH_LOCATIONS = locations.toArray(new ConfigDataLocation[0]);
}
```
### ConfigurationProperties validate source 
- org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor.postProcessBeforeInitialization
- org.springframework.boot.context.properties.ConfigurationPropertiesBinder.bind
- org.springframework.boot.context.properties.ConfigurationPropertiesBinder.getValidators
- org.springframework.boot.context.properties.ConfigurationPropertiesJsr303Validator
- org.springframework.boot.context.properties.bind.validation.ValidationBindHandler.validateAndPush