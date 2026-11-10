package com.yash.goal_tracker;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoalTrackerApplication {

	public static void main(String[] args) {
		// Configuration of dotenv-java 
		// This next line configured the dotenv, ignoring any missing parans.
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach((entry) -> 
			System.setProperty(entry.getKey(), entry.getValue())
		);
		SpringApplication.run(GoalTrackerApplication.class, args);
	}

}
