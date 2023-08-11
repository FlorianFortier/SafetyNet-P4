package com.safetyNet.alerts.api;

import com.safetyNet.alerts.api.service.PersonService;
import com.safetyNet.alerts.api.util.ReadDataFromJson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafetyNetAlertsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}


}
