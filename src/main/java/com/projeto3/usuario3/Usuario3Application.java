package com.projeto3.usuario3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.projeto3")
@EnableJpaRepositories(basePackages = "com.projeto3.repository")
@EntityScan(basePackages = "com.projeto3.entity")
public class Usuario3Application {

	public static void main(String[] args) {
		SpringApplication.run(Usuario3Application.class, args);
	}
}
