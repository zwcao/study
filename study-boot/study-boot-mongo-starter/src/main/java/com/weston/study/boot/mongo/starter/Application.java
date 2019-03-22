package com.weston.study.boot.mongo.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class }, scanBasePackages = "com.weston.study.boot.mongo")
public class Application {
	
	public static void main(String[] args) {
//		System.setProperty("debug", "true");
		SpringApplication.run(Application.class, args);
	}
}
