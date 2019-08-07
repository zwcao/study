package com.weston.study.boot.netty.web.starter;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class}, scanBasePackages = "com.weston.study")
public class HttpServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HttpServerApplication.class).web(WebApplicationType.NONE).run(args);
    }

}
