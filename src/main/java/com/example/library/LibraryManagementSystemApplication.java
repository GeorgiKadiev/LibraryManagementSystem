package com.example.library;

import com.example.library.console.ConsoleInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class LibraryManagementSystemApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(LibraryManagementSystemApplication.class);
	private final ConsoleInterface consoleInterface;
	private final Environment environment;

	public LibraryManagementSystemApplication(ConsoleInterface consoleInterface, Environment environment) {
		this.consoleInterface = consoleInterface;
		this.environment = environment;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) {
		String[] activeProfiles = environment.getActiveProfiles();
		if (activeProfiles.length > 0) {
			logger.info("Active Profiles: {}", String.join(", ", activeProfiles));
		} else {
			logger.info("No active profiles set. Using default profile.");
		}

		if (consoleInterface != null) {
			logger.info("Starting ConsoleInterface...");
			consoleInterface.start();
		} else {
			logger.info("ConsoleInterface is not initialized. Skipping startup.");
		}
	}
}
