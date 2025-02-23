package com.donut.donutproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DonutProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(DonutProjectApplication.class, args);
	}
}