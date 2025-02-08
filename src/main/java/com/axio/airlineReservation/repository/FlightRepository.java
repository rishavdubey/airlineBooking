package com.axio.airlineReservation.repository;

import com.axio.airlineReservation.entity.Flight;
import com.axio.airlineReservation.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
}
