package com.axio.airlineReservation;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;


@SpringBootApplication
public class AirlineReservationApplication {
	private static final Logger logger= LoggerFactory.getLogger(AirlineReservationApplication.class);

	public static void main(String[] args) {
		try{
			String currDir= System.getProperty("user.dir");
			logger.info("curr Dir :: {} ", currDir);
			System.setProperty("spring.config.location",currDir + "/Config/application.properties");
		}catch (Exception e){
			logger.error("Error While loading Config");
		}
		SpringApplication.run(AirlineReservationApplication.class, args);


	}


}
