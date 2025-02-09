package com.axio.airlinereservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class AirlineReservationApplication {
	private static final Logger logger= LoggerFactory.getLogger(AirlineReservationApplication.class);

	public static void main(String[] args) {
		try{
			String currDir= System.getProperty("user.dir");
			logger.info("curr Dir :: {} ", currDir);
			System.setProperty("spring.config.location",currDir + "/Config/application.properties");
			SpringApplication.run(AirlineReservationApplication.class, args);
		}catch (Exception e){
			logger.error("Error While loading Config");
		}



	}


}
