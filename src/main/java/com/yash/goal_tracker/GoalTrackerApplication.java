package com.yash.goal_tracker;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Springboot decorator
public class GoalTrackerApplication {

	public static void main(String[] args) {
		// Load variables from the .env file into the dotenv object.
		// ignoreIfMissing() prevents a crash if the .env file doesn't exist (e.g. in Docker/CI).
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		// Spring Boot doesn't read .env files, but it does read JVM system properties.
		// Copy each entry across so ${DB_URL}-style placeholders in application.yaml resolve.
		// This must happen before SpringApplication.run, which reads config at startup.
		dotenv.entries().forEach((entry) ->
			System.setProperty(entry.getKey(), entry.getValue())
		);
		// The next line runs the app itself, with the class type (the definition, not an object) and its arguments
		SpringApplication.run(GoalTrackerApplication.class, args);
	}

}
