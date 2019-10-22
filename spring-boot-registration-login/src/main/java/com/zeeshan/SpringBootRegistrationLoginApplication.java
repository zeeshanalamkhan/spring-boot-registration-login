package com.zeeshan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringBootRegistrationLoginApplication {

	private static final Logger logger = LogManager.getLogger(SpringBootRegistrationLoginApplication.class);

	public static void main(String[] args) {
		logger.info("main() execution started");
		SpringApplication.run(SpringBootRegistrationLoginApplication.class, args);
		logger.info("main() execution completed");
	}

}
