package com.weston.study.boot.mybatis.starter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Configuration
@AutoConfigureAfter(DataSourceConfiguration.class)
@Import(SlaverDBAspect.class)
public class MybatisConfiguration extends MybatisAutoConfiguration {

	private static Log logger = LogFactory.getLog(MybatisConfiguration.class);

	public MybatisConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider,
			ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider,
			ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
		super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
	}

	@Resource(name = "masterDataSource")
	private DataSource masterDataSource;

	@Resource(name = "slaveDataSource")
	private DataSource slaveDataSource;

	@Value("${mybatis.mapperScanner.basePackage}")
	private String mapperScanBasePackage;

	@Bean
	public SqlSessionFactory nsqlSessionFactory() throws Exception {
		logger.info("-------------------- 重载父类 sqlSessionFactory init---------------------");
		System.out.println("构建SqlSessionFactory");
		SqlSessionFactory factory = super.sqlSessionFactory(roundRobinDataSouceProxy());
		factory.getConfiguration().addInterceptor(new AuditingInterceptor());
		return factory;
	}

	@Bean
	public AbstractRoutingDataSource roundRobinDataSouceProxy() {
		RoutingDataSource proxy = new RoutingDataSource();
		Map<Object, Object> targetDataResources = new HashMap<Object, Object>();
		targetDataResources.put(DBContextHolder.DbType.MASTER, masterDataSource);
		targetDataResources.put(DBContextHolder.DbType.SLAVE, slaveDataSource);
		proxy.setDefaultTargetDataSource(masterDataSource);// 默认源
		proxy.setTargetDataSources(targetDataResources);
		return proxy;
	}

	@Intercepts({
			@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
	private static class AuditingInterceptor implements Interceptor {

		@Override
		public Object intercept(Invocation invocation) throws Throwable {
			Object[] args = invocation.getArgs();
			MappedStatement mappedStatement = (MappedStatement) args[0];
			Object paramObject = args[1];
			// TODO 参数处理
			return invocation.proceed();
		}

		@Override
		public Object plugin(Object target) {
			return Plugin.wrap(target, this);
		}

		@Override
		public void setProperties(Properties properties) {
		}

	}
}
