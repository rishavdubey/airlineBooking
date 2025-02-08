package com.axio.airlineReservation.controller;

import com.axio.airlineReservation.common.CommonValidation;
import com.axio.airlineReservation.entity.Flight;
import com.axio.airlineReservation.request.flight.FlightRequest;
import com.axio.airlineReservation.response.CustomMessage;
import com.axio.airlineReservation.service.FlightService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flights")
class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private CommonValidation commonValidation;

    @PostMapping("/add")
    public ResponseEntity<CustomMessage> addFlight(@RequestBody FlightRequest flightRequest,@CookieValue(name = "JWT_TOKEN", required = false) String token)  {
        commonValidation.validateToken(token);
        String message=flightService.addFlight(flightRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(message,"200"));
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAvailableFlights() {
        List<Flight> flights=flightService.getAllFlight();
        return ResponseEntity.status(HttpStatus.OK).body(flights);
    }

    @GetMapping("/fares")
    public ResponseEntity<Map<String,Double>> getFareDetails(@RequestParam("flightId") String id) {
        Double price=flightService.getFlightFare(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("fare", price));

    }
}