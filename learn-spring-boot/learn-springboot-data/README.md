# Features list
## spring-data-jdbc: JdbcTemplate Usage
- com.example.learn.springboo.data.repository.impl.CustomerDaoWithJdbcTemplate

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
    - 方法1：com.example.learn.springboo.data.repository.impl.CustomerRepository#update
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