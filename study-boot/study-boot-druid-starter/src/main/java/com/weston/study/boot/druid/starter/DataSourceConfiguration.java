package com.weston.study.boot.druid.starter;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DataSourceConfiguration {
	
	@Primary
	@Bean(name="masterDS")
	@ConfigurationProperties("druid.datasource")
	public DataSource masterDataSource() {
		return DruidDataSourceBuilder.create().build();
	}
	
	@Bean(name="slaveDS1")
	@ConditionalOnProperty("druid.datasource.slave.one")
	@ConfigurationProperties(prefix = "druid.datasource.slave.one")
	public DataSource slaveDataSourceOne() {
		return DruidDataSourceBuilder.create().build();
	}
	
	@Bean(name="slaveDS2")
	@ConditionalOnProperty("druid.datasource.slave.two")
	@ConfigurationProperties(prefix = "druid.datasource.slave.two")
	public DataSource slaveDataSourceTwo() {
		return DruidDataSourceBuilder.create().build();
	}
	
	@Bean(name="routeDataSource")
	public RoutingDataSource routeDataSource(@Qualifier("masterDS")DataSource masterDataSource, @Nullable @Qualifier("slaveDS1")DataSource slaveDataSourceOne, @Nullable @Qualifier("slaveDS2")DataSource slaveDataSourceTwo) {
		Map<Object, Object> targetDataResources = new HashMap<Object, Object>();
		targetDataResources.put(0, masterDataSource);
		int slaveCount = 0;
		if(slaveDataSourceOne != null) {
			targetDataResources.put(1, slaveDataSourceOne);
			slaveCount ++;
		}
		if(slaveDataSourceTwo != null) {
			slaveCount ++;
			targetDataResources.put(2, slaveDataSourceTwo);
		}
		RoutingDataSource proxy = new RoutingDataSource(slaveCount);
		// 默认源
		proxy.setDefaultTargetDataSource(masterDataSource);
		proxy.setTargetDataSources(targetDataResources);
		return proxy;
	}

}
