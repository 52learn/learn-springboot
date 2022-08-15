# Feature List
## Customize Conditional 
1. Define OnEnabledXXXCondition class
com.example.learn.springboot.sms.autoconfigure.OnEnabledSmsProviderCondition

2. Define ConditionalOnEnabledXXX class
com.example.learn.springboot.sms.autoconfigure.ConditionalOnEnabledSmsProvider

3. Define XXXAutoConfiguration class
com.example.learn.springboot.sms.autoconfigure.SmsProviderAutoConfiguration

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
## http request @RequestParam with format string, via customize StringToLaptopConvert
- com.example.learn.springboot.conversion.convert.StringToLaptopConvert
- com.example.learn.springboot.conversion.convert.MyConvertAutoConfiguration
```
http://127.0.0.1:8080/convert/param?laptop=notepad,15
```

- org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.EnableWebMvcConfiguration#mvcConversionService
springweb-mvc应用中,创建WebConversionService(为GenericConversionService子类) 注入容器:
```
@Bean
@Override
public FormattingConversionService mvcConversionService() {
    Format format = this.mvcProperties.getFormat();
    WebConversionService conversionService = new WebConversionService(new DateTimeFormatters()
            .dateFormat(format.getDate()).timeFormat(format.getTime()).dateTimeFormat(format.getDateTime()));
    addFormatters(conversionService);
    return conversionService;
}
```

- org.springframework.core.convert.support.GenericConversionService#addConverter(org.springframework.core.convert.converter.Converter<?,?>)
添加Converter时会将其包装转换为GenericConverter类型实例，所以WebConversionService中所有converters均为GenericConverter类型

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


## support PropertiesHttpMessageConvert with properties representation
- com.example.learn.springboot.web.PropertiesHttpMessageConverter
    - 服务端请求接受MediaType: "Content-Type:text/plain"
    - 客户端响应接受MediaType: "Accept: text/plain"
实现properties格式字符串传输，服务端通过自定义HttpMessageConverter(PropertiesHttpMessageConvert)将请求内容转换为Properties对象，在endpoint中直接接收Properties参数对象；
```
@PostMapping(value = "/person/properties")
public Properties postPerson(@RequestBody Properties person)
```    
```
curl -v -X POST  http://127.0.0.1:8080/person/properties \
-H "Content-Type:text/plain" \
-H "Accept: text/plain" \
-d 'person.name=Gates\
person.age=18'
```


## Customize @PropertySource support yaml file
- com.example.learn.springboot.properties.YamlPropertySourceFactory
- com.example.learn.springboot.properties.PropertySourceUsageAutoConfiguration.PropertySourceWithYamlConfiguration


reference:  
https://www.baeldung.com/spring-yaml-propertysource


## Use ObjectProvider

reference:  
Using Spring's ObjectProvider : http://rahulsh1.github.io/tech/2018/12/20/Using-Spring-ObjectProvider 

## Access the concrete class from a spring proxy
https://solidsyntax.be/2013/12/10/access-concrete-class-spring-proxy/

## AOP Usage
- By using @Aspect
    - com.example.learn.springboot.aop.LoggingAspect
     
    - com.example.learn.springboot.aop.MyInterceptorAutoConfiguration#loggingAspect

Advisor = Advice + Pointcut 
    
- By using implementing MethodInterceptor interface
    - com.example.learn.springboot.aop.TracingInterceptor
    - com.example.learn.springboot.aop.MyInterceptorAutoConfiguration.controllerAdvisor
    1. define pointcut expression  
    2. create Advisor @Bean
    
Advisor = Interceptor + Pointcut
Advice: @Before,@After,Around


# Learn Knowledge

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


### 业务开发中的Controller类的@RequestMapping方法(endpoint)生成HandlerMethod实例化过程
依附于RequestMappingHandlerMapping#afterPropertiesSet初始化方法中实现的
- org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping#afterPropertiesSet
- org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#initHandlerMethods
  获取容器中所有beanNames,循环遍历获取beanName
- ->>> org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#processCandidateBean
- ->>>>> org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#isHandler
   判断类上是否有@Controller或者@RequestMapping注解
- ->>>>> org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#detectHandlerMethods
    找到所有@RequestMapping方法、并调用方法注册HandlerMethod
- ->>>>>>> org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping#registerHandlerMethod
- .......
- ->>>>>>> org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.MappingRegistry#register
创建HandlerMethod，验证RequestMappingInfo，最终注册到MappingRegistry里

注意点：
- org.springframework.web.method.HandlerMethod#bean 
字段为Object类型，表示对应endpoint方法所属的controller实例对象，可以为Controller类名（beanName)或者具体Controller对象类型；
运行时存储对象HandlerMethod结构(部分)：
```
handler = {HandlerMethod@7478} "com.example.learn.springboot.web.WebController#postPerson(Person)"
 bean = {WebController@8620} 
 beanFactory = {DefaultListableBeanFactory@6635} "org.springframework.beans.factory.support.DefaultListableBeanFactory@200a26bc: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory,learnSpringBootApplication,org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory,org.springframework.cache.annotation.ProxyCachingConfiguration,org.springframework.cache.config.internalCacheAdvisor,cacheOperationSource,cacheInterceptor,com.example.learn.springboot.enable.SomeBeanConfiguration,bean1,com.example.learn.springboot.enable.Module1Configuration,moudle1Name,com.example.learn.springboot.enable.Module2Configuration,moudle2Name,org.springframework.boot.autoconfigure.AutoConfigurationPackages,org.springframework.aop"
 messageSource = {AnnotationConfigServletWebServerApplicationContext@6636} "org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@7e38a7fe, started on Sat Jul 23 15:35:15 CST 2022"
 beanType = {Class@6735} "class com.example.learn.springboot.web.WebController"
 method = {Method@8621} "public com.example.learn.springboot.web.WebController$Person com.example.learn.springboot.web.WebController.postPerson(com.example.learn.springboot.web.WebController$Person)"
 bridgedMethod = {Method@8621} "public com.example.learn.springboot.web.WebController$Person com.example.learn.springboot.web.WebController.postPerson(com.example.learn.springboot.web.WebController$Person)"
 parameters = {MethodParameter[1]@8622} 
  0 = {HandlerMethod$HandlerMethodParameter@8629} "method 'postPerson' parameter 0"
 responseStatus = null
 responseStatusReason = null
```
- org.springframework.web.servlet.HandlerExecutionChain#handler
字段为Object类型，为HandlerMethod
 
### Controller类的Endpoint方法修饰符不正确抛异常
- org.springframework.aop.support.AopUtils#selectInvocableMethod 

### 存在重复endpoint抛出异常
- org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.MappingRegistry#validateMethodMapping
从org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.MappingRegistry#registry map集合中获取当前RequestMappingInfo的HandlerMethod，若存在则抛出异常：
```
private void validateMethodMapping(HandlerMethod handlerMethod, T mapping) {
    MappingRegistration<T> registration = this.registry.get(mapping);
    HandlerMethod existingHandlerMethod = (registration != null ? registration.getHandlerMethod() : null);
    if (existingHandlerMethod != null && !existingHandlerMethod.equals(handlerMethod)) {
        throw new IllegalStateException(
                "Ambiguous mapping. Cannot map '" + handlerMethod.getBean() + "' method \n" +
                handlerMethod + "\nto " + mapping + ": There is already '" +
                existingHandlerMethod.getBean() + "' bean method\n" + existingHandlerMethod + " mapped.");
    }
} 
```
### RequestMappingHandlerMapping 中添加Interceptors的过程
分两阶段添加Interceptors：  
阶段一：实例化RequestMappingHandlerMapping过程中
- org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#requestMappingHandlerMapping
- org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#getInterceptors

阶段二：初始化RequestMappingHandlerMapping之前，在ApplicationContextAware回调中：
- org.springframework.context.support.ApplicationContextAwareProcessor#postProcessBeforeInitialization
- org.springframework.context.support.ApplicationContextAwareProcessor#invokeAwareInterfaces
- org.springframework.context.support.ApplicationObjectSupport#setApplicationContext
- org.springframework.web.servlet.handler.AbstractHandlerMapping#initApplicationContext
- org.springframework.web.servlet.handler.AbstractHandlerMapping#detectMappedInterceptors
``` 
/**
 * Detect beans of type {@link MappedInterceptor} and add them to the list
 * of mapped interceptors.
 * <p>This is called in addition to any {@link MappedInterceptor}s that may
 * have been provided via {@link #setInterceptors}, by default adding all
 * beans of type {@link MappedInterceptor} from the current context and its
 * ancestors. Subclasses can override and refine this policy.
 * @param mappedInterceptors an empty list to add to
 */
protected void detectMappedInterceptors(List<HandlerInterceptor> mappedInterceptors) {
    mappedInterceptors.addAll(BeanFactoryUtils.beansOfTypeIncludingAncestors(
            obtainApplicationContext(), MappedInterceptor.class, true, false).values());
}
```
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


### org.springframework.web.HttpMediaTypeNotSupportedException  httpStatusCode: 415
Exception Desc：Resolved [org.springframework.web.HttpMediaTypeNotSupportedException: Content type 'text/plain;charset=UTF-8' not supported]
- org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver#readWithMessageConverters(org.springframework.http.HttpInputMessage, org.springframework.core.MethodParameter, java.lang.reflect.Type)

### select httpmessageConvert to convert response content

- org.springframework.http.converter.HttpMessageConverter#canWrite
参数mediaType为http请求头的Accept值，用该方法过滤获得HttpMessageConverter
```
/**
 * Indicates whether the given class can be written by this converter.
 * @param clazz the class to test for writability
 * @param mediaType the media type to write (can be {@code null} if not specified);
 * typically the value of an {@code Accept} header.
 * @return {@code true} if writable; {@code false} otherwise
 */
boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
```

- org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor#writeWithMessageConverters(T, org.springframework.core.MethodParameter, org.springframework.http.server.ServletServerHttpRequest, org.springframework.http.server.ServletServerHttpResponse)
selectedMediaType 为http请求头的Accept值，过滤获得HttpMessageConverter后，调用write方法写入响应流；	
```
if (selectedMediaType != null) {
    selectedMediaType = selectedMediaType.removeQualityValue();
    for (HttpMessageConverter<?> converter : this.messageConverters) {
        GenericHttpMessageConverter genericConverter = (converter instanceof GenericHttpMessageConverter ?
                (GenericHttpMessageConverter<?>) converter : null);
        if (genericConverter != null ?
                ((GenericHttpMessageConverter) converter).canWrite(targetType, valueType, selectedMediaType) :
                converter.canWrite(valueType, selectedMediaType)) {
            body = getAdvice().beforeBodyWrite(body, returnType, selectedMediaType,
                    (Class<? extends HttpMessageConverter<?>>) converter.getClass(),
                    inputMessage, outputMessage);
            if (body != null) {
                Object theBody = body;
                LogFormatUtils.traceDebug(logger, traceOn ->
                        "Writing [" + LogFormatUtils.formatValue(theBody, !traceOn) + "]");
                addContentDispositionHeader(inputMessage, outputMessage);
                if (genericConverter != null) {
                    genericConverter.write(body, targetType, selectedMediaType, outputMessage);
                }
                else {
                    ((HttpMessageConverter) converter).write(body, selectedMediaType, outputMessage);
                }
            }
            else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Nothing to write: null body");
                }
            }
            return;
        }
    }
}
```

###  GenericConverter, Converter, WebConversionService,HttpMessageConverter 
WebConversionService包含N个GenericConverter，作为门户接口类，并注入到容器中，同时提供对象转换方法；HttpMessageConverter 可以通过注入获得WebConversionService对象，调用其转换方法实现对象转换；

### lookup HandlerExecutionChain
HandlerExecutionChain contains a HandlerMethod(Handler)  and  a list of HandlerInterceptor
- org.springframework.web.servlet.handler.AbstractHandlerMapping#getHandler
- ->>> org.springframework.web.servlet.handler.AbstractHandlerMapping#getHandlerInternal
lookup Handler(HandlerMethod) with request
- ->>> org.springframework.web.servlet.handler.AbstractHandlerMapping.getHandlerExecutionChain
Initialize HandlerExecutionChain with handler and request，add interceptors


### XXXExposingInterceptor
Such as :
- org.springframework.web.servlet.resource.ResourceUrlProviderExposingInterceptor
- org.springframework.web.servlet.handler.ConversionServiceExposingInterceptor
 
