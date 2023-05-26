package com.befrank.casedeveloperjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BridgerJavaApplication {

	private static final Logger log = LoggerFactory.getLogger(BridgerJavaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BridgerJavaApplication.class, args);
	}

}
