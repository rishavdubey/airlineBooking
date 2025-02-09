package com.axio.airlinereservation.repository;

import com.axio.airlinereservation.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,String> {
    Optional<Booking> findByReservationId(String reservationId);
}
