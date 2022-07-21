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
- 定义检测类  
  - option 1: com.example.learn.springboot.diagnostics.ProjectConstraintViolationApplicationRunner: do check until app startup  
  - option 2: com.example.learn.springboot.diagnostics.DiagnosticsApplicationListener: do check once ApplicationEnvironment Prepared (receive ApplicationEnvironmentPreparedEvent) 

- com.example.learn.springboot.diagnostics.ProjectConstraintViolationException
- com.example.learn.springboot.diagnostics.ProjectConstraintViolationFailureAnalyzer
- spring.factories
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.learn.springboot.diagnostics.DiagnosticsAutoConfiguration

org.springframework.context.ApplicationListener=\
com.example.learn.springboot.diagnostics.DiagnosticsApplicationListener  

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
case 1: (Preferred)
- com.example.learn.springboot.yaml.load.YamlLoadApplicationListener#getPropertySourceByYamlPropertiesFactoryBean

case 2:
- com.example.learn.springboot.yaml.load.YamlLoadApplicationListener#getPropertySourceByYamlPropertySourceLoader


## Custom Validator 
- com.example.learn.springboot.validation.UserNameUniqueValidator
- com.example.learn.springboot.validation.UserNameUnique
- com.example.learn.springboot.validation.ValidationAutoConfiguration

```
curl -H "Content-Type:application/json" -X POST -d '{"userName": "kim"}' http://127.0.0.1:8080/regist
```
reference:  
http://dolszewski.com/spring/custom-validation-annotation-in-spring/ 

## Custom Validator for ConfigurationProperties 
- com.example.learn.springboot.properties.validator.ReportConfigProperties

reference:  
https://www.grabanotherbyte.com/en/spring/2020/08/06/how-to-customize-configuration-properties-validation-in-spring.html

## Enable debug | trace logging for core loggers 
core loggers:
```
org.springframework.boot.context.logging.LoggingApplicationListener.DEFAULT_GROUP_LOGGERS
private static final Map<String, List<String>> DEFAULT_GROUP_LOGGERS;
static {
    MultiValueMap<String, String> loggers = new LinkedMultiValueMap<>();
    loggers.add("web", "org.springframework.core.codec");
    loggers.add("web", "org.springframework.http");
    loggers.add("web", "org.springframework.web");
    loggers.add("web", "org.springframework.boot.actuate.endpoint.web");
    loggers.add("web", "org.springframework.boot.web.servlet.ServletContextInitializerBeans");
    loggers.add("sql", "org.springframework.jdbc.core");
    loggers.add("sql", "org.hibernate.SQL");
    loggers.add("sql", "org.jooq.tools.LoggerListener");
    DEFAULT_GROUP_LOGGERS = Collections.unmodifiableMap(loggers);
}
```
two methods :
- method 1: 
```
java -jar myapp.jar --debug|--trace
```
- method 2:
application.yaml
```
trace|debug=true
```

reference  
https://docs.spring.io/spring-boot/docs/2.7.1/reference/html/features.html#features.logging.console-output


## import AutoConfiguration class
add to :  
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 

## http request @RequestBody with json content,supported with MappingJackson2HttpMessageConverter by default

```
curl -H "Content-Type:application/json" -v -X POST -d '{"brand":"ibm","inch":14,"outputs":[{"name":"monitor"},{"name":"mouse"}]}' http://127.0.0.1:8080/convert
```
# http request @RequestParam with format string, by custom StringToLaptopConvert
- com.example.learn.springboot.conversion.convert.StringToLaptopConvert
- com.example.learn.springboot.conversion.convert.MyConvertAutoConfiguration
```
http://127.0.0.1:8080/convert/param?laptop=notepad,15
``` 

## support xmlHttpMessageConverter with xml representation 
- create MarshallingHttpMessageConverter bean  
```
@Bean
public HttpMessageConverter<Object> xmlHttpMessageConverter() {
    MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
    XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
    xmlConverter.setMarshaller(xstreamMarshaller);
    xmlConverter.setUnmarshaller(xstreamMarshaller);
    return xmlConverter;
}
```
- add dependencies to pom.xml 
```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-oxm</artifactId>
    <version>5.3.21</version>
</dependency>
<dependency>
    <groupId>com.thoughtworks.xstream</groupId>
    <artifactId>xstream</artifactId>
    <version>1.4.11.1</version>
</dependency>
```

```
curl -H "Accept: application/xml" -v -X GET http://127.0.0.1:8080/person

curl -H "Accept: application/xml" -H "Content-Type:application/xml" -v -X POST -d "<com.example.learn.springboot.web.WebController_-Person><name>tom</name><age>27</age></com.example.learn.springboot.web.WebController_-Person>" POST http://127.0.0.1:8080/person
```


## Customize @PropertySource support yaml file
- com.example.learn.springboot.properties.YamlPropertySourceFactory
- com.example.learn.springboot.properties.PropertySourceUsageAutoConfiguration.PropertySourceWithYamlConfiguration


reference:  
https://www.baeldung.com/spring-yaml-propertysource

## Learn Knowledge

### 获取类名中泛型类型
org.springframework.boot.diagnostics.AbstractFailureAnalyzer#getCauseType
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

### Springboot extension
- com.example.learn.springboot.extension.MyApplicationContextInitializer
- com.example.learn.springboot.extension.MyBeanDefinitionRegistryPostProcessor


reference  
- Springboot启动扩展点超详细总结，再也不怕面试官问了   
https://segmentfault.com/a/1190000023033670     next: BeanDefinitionRegistryPostProcessor
- 深入理解BeanFactoryPostProcessor & BeanDefinitionRegistryPostProcessor 
https://mrbird.cc/%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3BeanFactoryPostProcessor-BeanDefinitionRegistryPostProcessor.html

### @Autowired @Value 注入的实现原理相关源码
org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessProperties

### XXXAware触发机制实现源码
XXXAware：  
EnvironmentAware,ResourceLoaderAware,ApplicationEventPublisherAware,MessageSourceAware,ApplicationStartupAware,ApplicationContextAware
- org.springframework.context.support.ApplicationContextAwareProcessor
org.springframework.context.support.ApplicationContextAwareProcessor.postProcessBeforeInitialization 中调用invokeAwareInterfaces方法：  
```
private void invokeAwareInterfaces(Object bean) {
    if (bean instanceof EnvironmentAware) {
        ((EnvironmentAware) bean).setEnvironment(this.applicationContext.getEnvironment());
    }
    if (bean instanceof EmbeddedValueResolverAware) {
        ((EmbeddedValueResolverAware) bean).setEmbeddedValueResolver(this.embeddedValueResolver);
    }
    if (bean instanceof ResourceLoaderAware) {
        ((ResourceLoaderAware) bean).setResourceLoader(this.applicationContext);
    }
    if (bean instanceof ApplicationEventPublisherAware) {
        ((ApplicationEventPublisherAware) bean).setApplicationEventPublisher(this.applicationContext);
    }
    if (bean instanceof MessageSourceAware) {
        ((MessageSourceAware) bean).setMessageSource(this.applicationContext);
    }
    if (bean instanceof ApplicationStartupAware) {
        ((ApplicationStartupAware) bean).setApplicationStartup(this.applicationContext.getApplicationStartup());
    }
    if (bean instanceof ApplicationContextAware) {
        ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
    }
}
```
### Bean Initialize 
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
```
protected Object initializeBean(String beanName, Object bean, @Nullable RootBeanDefinition mbd) {
    ···
    invokeAwareMethods(beanName, bean);

    Object wrappedBean = bean;
    wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);

    invokeInitMethods(beanName, wrappedBean, mbd);

    applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);

    return wrappedBean;
}
```

Trigger of BeanNameAware,BeanClassLoaderAware,BeanFactoryAware 
```
private void invokeAwareMethods(String beanName, Object bean) {
    if (bean instanceof Aware) {SmartInitializingSingleton 
        if (bean instanceof BeanNameAware) {
            ((BeanNameAware) bean).setBeanName(beanName);
        }
        if (bean instanceof BeanClassLoaderAware) {
            ClassLoader bcl = getBeanClassLoader();
            if (bcl != null) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
            }
        }
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
        }
    }
}
```
### FactoryBean 

FactoryBean is a programmatic contract. Implementations are no supposed to rely on annotation-driven injection or other reflective facilities.


### Instantiate all remaining (non-lazy-init) singletons
org.springframework.beans.factory.support.DefaultListableBeanFactory#preInstantiateSingletons

### refresh Spring ConfigurableApplicationContext  
org.springframework.context.support.AbstractApplicationContext#refresh  

### SpringMVC下的ConversionService(Convert)
- org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.EnableWebMvcConfiguration#mvcConversionService
- org.springframework.boot.autoconfigure.web.format.WebConversionService#WebConversionService
- org.springframework.format.support.DefaultFormattingConversionService#addDefaultFormatters
- org.springframework.boot.convert.ApplicationConversionService#addBeans

###
- org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter
- org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.EnableWebMvcConfiguration#requestMappingHandlerAdapter

### HttpMessageConverter VS Convert
- org.springframework.http.converter.HttpMessageConverter
Strategy interface for converting from and to HTTP requests (body) and responses (body).

- org.springframework.core.convert.converter.Converter


### main process of http request

- org.springframework.web.servlet.FrameworkServlet#processRequest
- org.springframework.web.servlet.DispatcherServlet#doService
- org.springframework.web.servlet.DispatcherServlet#doDispatch
- org.springframework.web.servlet.DispatcherServlet#getHandler
- End >>>>>org.springframework.web.servlet.DispatcherServlet#noHandlerFound
- org.springframework.web.servlet.DispatcherServlet#getHandlerAdapter
  End >>>>>throw ServletException, if no HandlerAdapter can be found for the handler. This is a fatal error.
- ->>> org.springframework.web.servlet.HandlerExecutionChain#applyPreHandle
  iterate interceptorList and invoke org.springframework.web.servlet.HandlerInterceptor#preHandle
  End >>>>>HandlerExecutionChain#applyPreHandle return false
- ->>> org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter#handle
- 	  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#handleInternal
-     org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#invokeHandlerMethod
- 	  >>> org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod#invokeAndHandle
- 		>>> org.springframework.web.method.support.InvocableHandlerMethod#invokeForRequest
- 		>>> org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite#handleReturnValue
        处理endpoint返回值，并写入到outstream流中



### 将http请求内容(RequestParam、RequestBody)转换为Endpoint的参数对象
org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver#resolveArgument
 
- org.springframework.web.method.annotation.RequestParamMethodArgumentResolver#resolveName
resolve http request param value 

- org.springframework.web.bind.support.DefaultDataBinderFactory#createBinder
create WebDataBinder

- org.springframework.web.bind.support.ConfigurableWebBindingInitializer#initBinder
init WebDataBinder
 
```
@Override
public void initBinder(WebDataBinder binder) {
	binder.setAutoGrowNestedPaths(this.autoGrowNestedPaths);
	if (this.directFieldAccess) {
		binder.initDirectFieldAccess();
	}
	if (this.messageCodesResolver != null) {
		binder.setMessageCodesResolver(this.messageCodesResolver);
	}
	if (this.bindingErrorProcessor != null) {
		binder.setBindingErrorProcessor(this.bindingErrorProcessor);
	}
	if (this.validator != null && binder.getTarget() != null &&
			this.validator.supports(binder.getTarget().getClass())) {
		binder.setValidator(this.validator);
	}
	if (this.conversionService != null) {
		binder.setConversionService(this.conversionService);
	}
	if (this.propertyEditorRegistrars != null) {
		for (PropertyEditorRegistrar propertyEditorRegistrar : this.propertyEditorRegistrars) {
			propertyEditorRegistrar.registerCustomEditors(binder);
		}
	}
}
```
- org.springframework.web.method.annotation.InitBinderDataBinderFactory#initBinder
Adds initialization to a WebDataBinder via @InitBinder methods.

- org.springframework.core.convert.support.GenericConversionService#convert(java.lang.Object, org.springframework.core.convert.TypeDescriptor, org.springframework.core.convert.TypeDescriptor)

### 获取http请求对应的 HandlerMethodArgumentResolver
- org.springframework.web.method.support.HandlerMethodArgumentResolverComposite#getArgumentResolver
比如：org.springframework.web.method.annotation.RequestParamMethodArgumentResolver#supportsParameter 的实现中去找endpoint中@RequestParam注解是否存在。


### endpoint返回值处理器的初始化配置
- org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#afterPropertiesSet
```
@Override
public void afterPropertiesSet() {
    // Do this first, it may add ResponseBody advice beans
    initControllerAdviceCache();

    if (this.argumentResolvers == null) {
        List<HandlerMethodArgumentResolver> resolvers = getDefaultArgumentResolvers();
        this.argumentResolvers = new HandlerMethodArgumentResolverComposite().addResolvers(resolvers);
    }
    if (this.initBinderArgumentResolvers == null) {
        List<HandlerMethodArgumentResolver> resolvers = getDefaultInitBinderArgumentResolvers();
        this.initBinderArgumentResolvers = new HandlerMethodArgumentResolverComposite().addResolvers(resolvers);
    }
    if (this.returnValueHandlers == null) {
        List<HandlerMethodReturnValueHandler> handlers = getDefaultReturnValueHandlers();
        this.returnValueHandlers = new HandlerMethodReturnValueHandlerComposite().addHandlers(handlers);
    }
}
```
 

### http request exception： Request Method xxx not supported
- org.springframework.web.servlet.support.WebContentGenerator#checkRequest



### The lastest upper level call before invoke Controller's endpoints
- org.springframework.web.method.support.InvocableHandlerMethod#doInvoke
```
Method method = getBridgedMethod();
try {
	if (KotlinDetector.isSuspendingFunction(method)) {
		return CoroutinesUtils.invokeSuspendingFunction(method, getBean(), args);
	}
	return method.invoke(getBean(), args);
}
```

### Invoke factory processors registered as beans in the context.
org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors


