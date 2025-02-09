package com.axio.airlinereservation.repository;

import com.axio.airlinereservation.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
    List<Flight> findByFromCityAndToCityAndDate(String fromCity, String toCity, LocalDate date);
}
