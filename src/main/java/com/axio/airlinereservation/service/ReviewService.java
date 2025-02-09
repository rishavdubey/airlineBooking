package com.axio.airlinereservation.service;

import com.axio.airlinereservation.exception.CustomException;
import com.axio.airlinereservation.common.UniqueIdGenerator;
import com.axio.airlinereservation.entity.Review;
import com.axio.airlinereservation.repository.ReviewRepository;
import com.axio.airlinereservation.request.review.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    public Review submitReview(String reservationId, ReviewRequest reviewRequest){
        reservationService.getReservationById(reservationId);
        Optional<Review> reviewOptional= reviewRepository.findByReservationId(reservationId);
        if(reviewOptional.isPresent()){
            throw new CustomException("Review already provided for reservationId : "+reservationId,"tech");
        }
        Review newReview=new Review();
        newReview.setReviewId("RVW"+uniqueIdGenerator.generateUniqueId());
        newReview.setReservationId(reservationId);
        newReview.setComment(reviewRequest.getComment());
        newReview.setRating(reviewRequest.getRating());
        newReview = reviewRepository.save(newReview);
        return newReview;
    }
    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }
}
