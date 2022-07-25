# Features list




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