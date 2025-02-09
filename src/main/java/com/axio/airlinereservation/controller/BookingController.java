package com.axio.airlinereservation.controller;

import com.axio.airlinereservation.common.CommonValidation;
import com.axio.airlinereservation.response.BookingResponse;
import com.axio.airlinereservation.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/booking")
class BookingController {

    @Autowired
    private CommonValidation commonValidation;
    @Autowired
    private BookingService bookingService;

    @PostMapping("/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(@RequestParam("reservationId") String reservationId, @CookieValue(name = "JWT_TOKEN", required = false) String token) {
        commonValidation.validateToken(token);
        BookingResponse bookingResponse=bookingService.confirmBooking(reservationId);
        return ResponseEntity.status(HttpStatus.OK).body(bookingResponse);
    }
}