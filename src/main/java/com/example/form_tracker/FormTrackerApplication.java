package com.example.form_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FormTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormTrackerApplication.class, args);
	}

}
