package com.weston.study.boot.mybatis.starter;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class }, scanBasePackages = "com.weston.study.boot.mybatis")
@MapperScan("${mybatis.mapperScanner.basePackage}")
public class Application {

	public static void main(String[] args) {
		System.setProperty("debug", "true");
		SpringApplication.run(Application.class, args);
	}
}

@Component
class T implements CommandLineRunner {
	@Autowired
	private SqlSessionFactory session;

	@Autowired
	@Qualifier("masterDataSource")
	private DataSource ds;
	
	@Autowired
	private GiWaveDao dao;

	@Override
	public void run(String... args) throws Exception {
		List<Object> obj = dao.query();
		System.out.println(obj);
	}

}

interface GiWaveDao {
	public List<Object> query();
}