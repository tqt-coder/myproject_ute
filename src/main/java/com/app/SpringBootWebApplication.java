package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Properties;

/**
 * @author Sarvesh Kumar
 * Main class to run application,
 */

@SpringBootApplication
public class SpringBootWebApplication {

	public static void main(String... args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

}