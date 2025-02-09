package com.axio.airlinereservation.controller;

import com.axio.airlinereservation.common.CommonValidation;
import com.axio.airlinereservation.entity.Flight;
import com.axio.airlinereservation.request.flight.FlightRequest;
import com.axio.airlinereservation.response.CustomMessage;
import com.axio.airlinereservation.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CustomMessage> addFlight(@Valid @RequestBody FlightRequest flightRequest, @CookieValue(name = "JWT_TOKEN", required = false) String token)  {
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