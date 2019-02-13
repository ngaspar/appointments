package com.sesame.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Entry point for this application.
 * 
 * @author ngaspar
 * @version 1.0
 */
@EnableAutoConfiguration
@SpringBootApplication
@EnableJpaAuditing
public class AppointmentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentsApplication.class, args);
	}
}
