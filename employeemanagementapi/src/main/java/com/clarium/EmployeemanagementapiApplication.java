package com.clarium;

import com.clarium.dto.EmailConfigurations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.*.*")
@EnableScheduling
@EnableConfigurationProperties(EmailConfigurations.class)
public class EmployeemanagementapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeemanagementapiApplication.class, args);
	}

}