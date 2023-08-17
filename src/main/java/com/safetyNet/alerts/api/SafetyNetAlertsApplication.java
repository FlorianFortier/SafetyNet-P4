package com.safetyNet.alerts.api;

import com.safetyNet.alerts.api.service.PersonService;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafetyNetAlertsApplication {
	// Define a static logger variable so that it references the
	// Logger instance named "SafetyNetAlertsApplication".
	private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(SafetyNetAlertsApplication.class, args);
		System.out.println("-------------------------------------------");
		System.out.println("WELCOME TO SAFETY NET ALERT API VERSION 1.0.0");
		System.out.println("-------------------------------------------");




		// Set up a simple configuration that logs on the console.
		logger.info("Entering application.");

	}

}
