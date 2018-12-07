一、如何配置Filter
你可以通过 spring.datasource.druid.filters=stat,wall,log4j ... 的方式来启用相应的内置Filter，不过这些Filter都是默认配置。如果默认配置不能满足你的需求，你可以放弃这种方式，通过配置文件来配置Filter，下面是例

# 配置StatFilter 
spring.datasource.druid.filter.stat.db-type=h2
spring.datasource.druid.filter.stat.log-slow-sql=true
spring.datasource.druid.filter.stat.slow-sql-millis=2000

# 配置WallFilter 
spring.datasource.druid.filter.wall.enabled=true
spring.datasource.druid.filter.wall.db-type=h2
spring.datasource.druid.filter.wall.config.delete-allow=false
spring.datasource.druid.filter.wall.config.drop-table-allow=false

# 其他 Filter 配置不再演示


目前为以下 Filter 提供了配置支持，请参考文档或者根据IDE提示（spring.datasource.druid.filter.*）进行配置。

StatFilter
WallFilter
ConfigFilter
EncodingConvertFilter
Slf4jLogFilter
Log4jFilter
Log4j2Filter
CommonsLogFilter


#如何获取 Druid 的监控数据
