package com.humanconsulting.humancore_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HumanCoreApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumanCoreApiApplication.class, args);
	}

}
