package com.example.demo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.demo.backend.persistence.repositories"
)
@EnableTransactionManagement
@EntityScan(basePackages = "com.example.demo.backend.persistence.domain.backend")
@PropertySource("file:///${user.home}/Downloads/spring_course/application-common.properties")
public class ApplicationConfig {


}
