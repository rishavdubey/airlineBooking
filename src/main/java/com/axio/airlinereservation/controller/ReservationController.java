package com.axio.airlinereservation.controller;

import com.axio.airlinereservation.common.CommonValidation;
import com.axio.airlinereservation.entity.Reservation;
import com.axio.airlinereservation.request.reservation.ReservationRequest;
import com.axio.airlinereservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
class ReservationController {

    @Autowired
    private CommonValidation commonValidation;
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestParam("flightId") String flight,
                                                         @Valid @RequestBody ReservationRequest reservationRequest,
                                                         @CookieValue(name = "JWT_TOKEN", required = false) String token) {
        String userName= commonValidation.validateToken(token);
        Reservation reservation=reservationService.createReservation(userName,flight,reservationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(reservation);
    }
}