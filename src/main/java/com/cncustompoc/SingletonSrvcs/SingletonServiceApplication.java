package com.cncustompoc.SingletonSrvcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
//@Configuration
@EnableScheduling
//@PropertySource(value = {"WEB-INF/application.properties"})
public class SingletonServiceApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SingletonServiceApplication.class);
    }
    /*
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        // <some context configuration>
        ServletRegistration.Dynamic spring = container.addServlet("Spring", new DispatcherServlet(context));
        // <some servlet configuration>
        // Here, set desired context class using 'contextClass' parameter.
        spring.setInitParameter("contextClass", context.getClass().getName());
        container.addListener(new ContextLoaderListener(context));
    }
    */
    public static void main(String[] args) {
        SpringApplication.run(SingletonServiceApplication.class, args);
    }
}