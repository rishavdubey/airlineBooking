package com.axio.airlinereservation.controller;

import com.axio.airlinereservation.common.CommonValidation;
import com.axio.airlinereservation.entity.Review;
import com.axio.airlinereservation.request.review.ReviewRequest;
import com.axio.airlinereservation.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
class ReviewController {

    @Autowired
    private CommonValidation commonValidation;

    @Autowired
    private ReviewService reviewService;


    @PostMapping("/submit")
    public ResponseEntity<Review> submitReview(@RequestParam("reservationId") String reservationId, @Valid @RequestBody ReviewRequest reviewRequest, @CookieValue(name = "JWT_TOKEN", required = false) String token) {
        commonValidation.validateToken(token);
        Review review=reviewService.submitReview(reservationId,reviewRequest);
        return ResponseEntity.status(HttpStatus.OK).body(review);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getAllReviews());
    }
}