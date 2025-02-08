package com.axio.airlineReservation.repository;

import com.axio.airlineReservation.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,String> {
    Optional<Booking> findByReservationId(String reservationId);
}
