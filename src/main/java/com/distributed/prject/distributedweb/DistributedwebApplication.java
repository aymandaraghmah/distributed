package com.distributed.prject.distributedweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/*@SpringBootApplication()
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})*/
/*
*/
/*
@ComponentScan
*/

//@EnableWebSecurity
/*
@EntityScan(basePackages = {"com.distributed.prject.distributedweb.model","com.distributed.prject.distributedweb.repository"})  // scan JPA entities
*/
/*@EnableJpaRepositories
@Configuration*/
@SpringBootApplication
@EnableAutoConfiguration
/*
@EnableGlobalMethodSecurity(prePostEnabled = true)*/
public class DistributedwebApplication extends
        SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DistributedwebApplication.class, args);
    }
}
