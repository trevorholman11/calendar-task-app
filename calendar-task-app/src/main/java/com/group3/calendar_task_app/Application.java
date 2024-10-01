/**
 * This class is used as the main entry point for the Calendar Task Manager application.
 * This class bootstraps the Spring Boot application by calling 
 * SpringApplication.run() which launches the embedded server.
 */
package com.group3.calendar_task_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	/**
	 * Launches the SpringBootApplication
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
