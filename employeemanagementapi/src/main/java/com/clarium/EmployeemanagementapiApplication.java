package com.clarium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.*.*")
public class EmployeemanagementapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeemanagementapiApplication.class, args);
	}

}