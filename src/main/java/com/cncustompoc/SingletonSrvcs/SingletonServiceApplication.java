package com.cncustompoc.SingletonSrvcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
@PropertySource(value = { "WEB-INF/application.properties" })
public class SingletonServiceApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
/*
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SingletonServiceApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(SingletonServiceApplication.class, args);
	}
*/
}