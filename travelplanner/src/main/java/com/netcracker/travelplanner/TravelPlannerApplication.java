package com.netcracker.travelplanner;

import com.netcracker.travelplanner.services.ErrorSavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelPlannerApplication {

	public static void main(String[] args) {

		SpringApplication.run(TravelPlannerApplication.class, args);
	}
}
