package com.axio.airlineReservation.service;

import com.axio.airlineReservation.Exception.CustomException;
import com.axio.airlineReservation.entity.Flight;
import com.axio.airlineReservation.repository.FlightRepository;
import com.axio.airlineReservation.request.flight.FlightRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public String  addFlight(FlightRequest flightRequest){
        String id=flightRequest.getId();
        String name=flightRequest.getName();
        String fromCity=flightRequest.getFromCity();
        String toCity= flightRequest.getToCity();
        double fare=flightRequest.getFare();
        int availableSeat=flightRequest.getAvailableSeat();

        if (flightRepository.existsById(id)) {
            throw new CustomException("Flight already exit for Id :: "+ id);
        }


        Flight flight=new Flight();
        flight.setId(id);
        flight.setFare(fare);
        flight.setName(name);flight.setFromCity(fromCity);
        flight.setToCity(toCity);
        flight.setAvailableSeat(availableSeat);
        flightRepository.save(flight);
        return "Flight added successfully!";

    }

    public List<Flight> getAllFlight(){
        return flightRepository.findAll();
    }

    public Double getFlightFare(String id){
        Optional<Flight> flight = flightRepository.findById(id);
        if (flight.isEmpty()) {
            throw new CustomException("Invalid Flight Id :: " + id);
        }
        return flight.map(Flight::getFare).orElse(null);

    }

    public Flight getFlightById(String flightId){
        Optional<Flight> flight = flightRepository.findById(flightId);
        if (flight.isEmpty()) {
            throw new CustomException("Invalid Flight Id :: " + flightId);
        }
        return flight.get();
    }

}
