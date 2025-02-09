package com.axio.airlinereservation.repository;

import com.axio.airlinereservation.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,String> {
    Optional<Review> findByReservationId(String reservationId);
}
