# Features list
## spring-data-jdbc: JdbcTemplate Usage
- com.example.learn.springboo.data.repository.impl.template.CustomerDaoWithJdbcTemplate

## spring-data-jdbc: Jdbc Repository Usage (like jpa and simpler)
- how to insert entity with save method
    - make sure entity implements org.springframework.data.domain.Persistable
    - make sure entity extends com.example.learn.springboo.data.repository.entity.PersistableEntity 
```
Customer insertCustomer = new Customer("XF00004", "娃哈哈集团", "0571-123456", 1, LocalDateTime.now());
insertCustomer.setInsert(true);
customerRepository.save(insertCustomer);
```    
- update entity 
    - 方法1：com.example.learn.springboo.data.repository.impl.repository.CustomerRepository#update
    ```
    public interface CustomerRepository extends CrudRepository<Customer,String> {
        @Modifying
        @Query("update customer set customer_phone=:customerPhone where customer_code=:customerCode")
        boolean update(@Param("customerCode") String customerCode,@Param("customerPhone") String customerPhone);
    
    }
    ```
    - 方法2：save方法 (推荐)


- reference 
    - Spring Data JDBC Tutorial with Examples：https://www.amitph.com/introduction-spring-data-jdbc/#insert_record_with_custom_id
    - Introduction to Spring Data JDBC：https://www.baeldung.com/spring-data-jdbc-intro


## create mybatis Interceptor
case 1: 修改次数自动更新拦截器
- com.example.learn.springboo.data.repository.interceptor.ModifyCountAutoIncrementInterceptor
- com.example.learn.springboo.data.repository.RepositoryAutoConfiguration.MybatisConfiguration#myInterceptor

case 2: encryption and decryption sensitive field
- com.example.learn.springboo.data.repository.interceptor.EncryptDecryptInterceptor



reference：
Mybatis Interceptor 拦截器 : https://segmentfault.com/a/1190000017393523
MyBatis拦截器原理探究: https://www.cnblogs.com/fangjian0423/p/mybatis-interceptor.html

## two methods create Mapper bean
- Define XXXMapper interface and Use @Mapper 
 com.example.learn.springboo.data.repository.impl.mapper.CustomerMapper
```
@Mapper
public interface CustomerMapper {
...
}
```
- Define XXXMapper interface and Use MapperFactoryBean
```
public interface MallOrderMapper {
...
}

@Bean
MapperFactoryBean<MallOrderMapper> mallOrderMapperFactoryBean(SqlSessionFactory sqlSessionFactory){
    MapperFactoryBean<MallOrderMapper> mallOrderMapperFactoryBean = new MapperFactoryBean<>(MallOrderMapper.class);
    mallOrderMapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
    return mallOrderMapperFactoryBean;
}

```
# Learn knowledge
## How to Instance HikariDataSource Bean
org.springframework.boot.jdbc.EmbeddedDatabaseConnection
- Select EmbeddedDatabaseConnection Enum instance in DataSourceProperties initialize method
    - org.springframework.boot.autoconfigure.jdbc.DataSourceProperties#afterPropertiesSet
-  Instance HikariDataSource 
org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.Hikari#dataSource




## ??? Generic Type Convert 
- org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.createDataSource
- org.springframework.boot.autoconfigure.jdbc.DataSourceProperties.initializeDataSourceBuilder
- org.springframework.boot.jdbc.DataSourceBuilder.type


## Instant by BeanUtils
- org.springframework.beans.BeanUtils#instantiateClass(java.lang.Class<T>)

## @ConfigurationProperties(prefix ="") 可用于@Bean实例化方法上
如：HikariDataSource Bean实例化时，通过@ConfigurationProperties(prefix = "spring.datasource.hikari") 将绑定yaml与配置对象字段
- org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.Hikari#dataSource
```
/**
 * Hikari DataSource configuration.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(HikariDataSource.class)
@ConditionalOnMissingBean(DataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.zaxxer.hikari.HikariDataSource",
        matchIfMissing = true)
static class Hikari {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    HikariDataSource dataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = createDataSource(properties, HikariDataSource.class);
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }

}
```
## @ConfigurationProperties配置对象绑定字段值(yaml|properties|Environment中)
- org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor#postProcessBeforeInitialization


## XXXAutoConfiguration and XXXConfiguration
- org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration
- org.springframework.boot.autoconfigure.jdbc.JdbcTemplateConfiguration
```
@AutoConfiguration(after = DataSourceAutoConfiguration.class)
@ConditionalOnClass({ DataSource.class, JdbcTemplate.class })
@ConditionalOnSingleCandidate(DataSource.class)
@EnableConfigurationProperties(JdbcProperties.class)
@Import({ DatabaseInitializationDependencyConfigurer.class, JdbcTemplateConfiguration.class,
		NamedParameterJdbcTemplateConfiguration.class })
public class JdbcTemplateAutoConfiguration {

}

```

## sql initialization 
- org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration

    - org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties

    - org.springframework.boot.autoconfigure.sql.init.DataSourceInitializationConfiguration

        - org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer
 
        - org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer#afterPropertiesSet 
        
## the default of schema and data sql 
- org.springframework.boot.autoconfigure.sql.init.SettingsCreator#scriptLocations
if not specialize "spring.sql.init.schema-locations" and "spring.sql.init.data-locations" ,then default is :
    - optional:classpath*:schema-all.sql
    - optional:classpath*:schema.sql
    - optional:classpath*:data-all.sql
    - optional:classpath*:data.sql
    
## the condition of run sql scripts  --- isEnable() logic
according the configuration：
```
spring.sql.init.mode=always|nerver|embedded
```
当为nerver 表示不初始化执行sql；   
当为always 表示始终初始化执行sql；  
当为embedded 需要执行isEmbeddedDatabase()验证判断是否为内嵌数据库，通过Connection中的url来判断，如：h2内嵌数据库的url需包含 :h2:mem  

- org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer#applyScripts

- org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer#isEnabled
```
private boolean isEnabled() {
    if (this.settings.getMode() == DatabaseInitializationMode.NEVER) {
        return false;
    }
    return this.settings.getMode() == DatabaseInitializationMode.ALWAYS || isEmbeddedDatabase();
}
```
- org.springframework.boot.jdbc.EmbeddedDatabaseConnection.IsEmbedded#doInConnection

## mybatis @Mapper instance bean process
- org.mybatis.spring.annotation.MapperScan
- org.mybatis.spring.annotation.MapperScannerRegistrar
    - org.mybatis.spring.annotation.MapperScannerRegistrar#registerBeanDefinitions
    register org.mybatis.spring.mapper.MapperScannerConfigurer beanDefinition

- org.mybatis.spring.mapper.MapperScannerConfigurer#postProcessBeanDefinitionRegistry
- org.springframework.context.annotation.ClassPathBeanDefinitionScanner#scan
- org.springframework.context.annotation.ClassPathBeanDefinitionScanner#doScan
    Perform a scan within the specified base packages and register beans (MapperFactoryBean) ,every Mapper has one MapperFactoryBean

- org.springframework.dao.support.DaoSupport#afterPropertiesSet
    MapperFactoryBean initialize method.
    - org.mybatis.spring.mapper.MapperFactoryBean#checkDaoConfig
    - org.apache.ibatis.binding.MapperRegistry#addMapper
    add Mapper to MapperRegistry
 
    - org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parse
        - org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#loadXmlResource
        加载XXXMapper.xml
- reference
Spring整合MyBatis（四）MapperFactoryBean 的创建: https://www.cnblogs.com/warehouse/p/9446054.html

## invoke the methods (@Select,@Update) of @Mapper interface 
- org.apache.ibatis.binding.MapperProxy.PlainMethodInvoker#invoke
- org.apache.ibatis.binding.MapperMethod#execute

## mybatis从ResultSet获取行转换为实体对象
- org.apache.ibatis.executor.resultset.DefaultResultSetHandler.getRowValue(org.apache.ibatis.executor.resultset.ResultSetWrapper, org.apache.ibatis.mapping.ResultMap, java.lang.String)
    - org.apache.ibatis.executor.resultset.DefaultResultSetHandler#createResultObject(org.apache.ibatis.executor.resultset.ResultSetWrapper, org.apache.ibatis.mapping.ResultMap, org.apache.ibatis.executor.loader.ResultLoaderMap, java.lang.String)
    - org.apache.ibatis.executor.resultset.DefaultResultSetHandler#shouldApplyAutomaticMappings
    - org.apache.ibatis.executor.resultset.DefaultResultSetHandler#applyPropertyMappings

## @NestedConfigurationProperty Usage
- org.mybatis.spring.boot.autoconfigure.MybatisProperties#configuration


## mybatis SqlSessionFactory instantiation
- org.mybatis.spring.SqlSessionFactoryBean#afterPropertiesSet
    - org.mybatis.spring.SqlSessionFactoryBean#buildSqlSessionFactory


## @ConditionalOnSingleCandidate
- org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate
- org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration

## FactoryBean Usecase
- org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration#sqlSessionFactory
create bean:
```
@Bean
@ConditionalOnMissingBean
public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    ...
    return factory.getObject();
}
``` 
- org.mybatis.spring.SqlSessionFactoryBean#getObject
SqlSessionFactoryBean implements  FactoryBean<SqlSessionFactory>, InitializingBean, override getObject() and afterPropertiesSet()
```
public class SqlSessionFactoryBean
    implements FactoryBean<SqlSessionFactory>, InitializingBean, ApplicationListener<ApplicationEvent> {
    @Override
    public SqlSessionFactory getObject() throws Exception {
        if (this.sqlSessionFactory == null) {
          afterPropertiesSet();
        }
        
        return this.sqlSessionFactory;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(dataSource, "Property 'dataSource' is required");
        notNull(sqlSessionFactoryBuilder, "Property 'sqlSessionFactoryBuilder' is required");
        state((configuration == null && configLocation == null) || !(configuration != null && configLocation != null),
            "Property 'configuration' and 'configLocation' can not specified with together");
        
        this.sqlSessionFactory = buildSqlSessionFactory();
    }
}
```
