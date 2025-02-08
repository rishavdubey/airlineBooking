package com.axio.airlineReservation.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
class ReviewController {
    private final Map<Integer, String> reviews = new HashMap<>();
    private int reviewCounter = 1;

//    @Min(1)
//@NotBlank
    @PostMapping("/submit")
    public String submitReview(@RequestParam  int reservationId, @RequestParam  String review) {
        reviews.put(reviewCounter++, "Review for reservation " + reservationId + ": " + review);
        return "Review submitted successfully for reservation ID: " + reservationId;
    }

    @GetMapping("/all")
    public Map<Integer, String> getAllReviews() {
        return reviews;
    }
}