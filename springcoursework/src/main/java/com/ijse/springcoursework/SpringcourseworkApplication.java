package com.ijse.springcoursework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.ijse.springcoursework.entity")


public class SpringcourseworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcourseworkApplication.class, args);
	}

}
