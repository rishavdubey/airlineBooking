package com.axio.airlineReservation.controller;

import com.axio.airlineReservation.common.CommonValidation;
import com.axio.airlineReservation.entity.Reservation;
import com.axio.airlineReservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
class ReservationController {

    @Autowired
    private CommonValidation commonValidation;
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestParam("flightId") String flight, @CookieValue(name = "JWT_TOKEN", required = false) String token) {
        String userName= commonValidation.validateToken(token);
        System.out.println("UserName  :: "+userName);
        System.out.println("Flight :: "+flight);
        Reservation reservation=reservationService.createReservation(userName,flight);
        return ResponseEntity.status(HttpStatus.OK).body(reservation);
    }
}