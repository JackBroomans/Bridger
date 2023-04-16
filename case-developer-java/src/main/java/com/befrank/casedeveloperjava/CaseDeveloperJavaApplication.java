package com.befrank.casedeveloperjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.stream.Stream;

@SpringBootApplication
@EnableJpaRepositories
public class CaseDeveloperJavaApplication {

	private static final Logger log = LoggerFactory.getLogger(CaseDeveloperJavaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CaseDeveloperJavaApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(DeelnemerRepository repository) {

		return args -> {
			log.info("Laden alle deelnemers:\n" + repository.findAll());
			log.info("Laden deelnemer:\n" + repository.findById(1));
		};
	}

}
