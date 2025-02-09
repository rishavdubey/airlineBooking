package com.axio.airlinereservation.service;

import com.axio.airlinereservation.exception.CustomException;
import com.axio.airlinereservation.entity.Flight;
import com.axio.airlinereservation.repository.FlightRepository;
import com.axio.airlinereservation.request.flight.FlightRequest;
import com.axio.airlinereservation.response.FlightsAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public String  addFlight(FlightRequest flightRequest){
        String id=flightRequest.getId();

        if (flightRepository.existsById(id)) {
            throw new CustomException("Flight already exit for Id :: "+ id);
        }

        Flight newflight=new Flight();
        newflight.setId(flightRequest.getId());
        newflight.setFare(flightRequest.getFare());
        newflight.setName(flightRequest.getName());
        newflight.setFromCity(flightRequest.getFromCity());
        newflight.setToCity(flightRequest.getToCity());
        newflight.setAvailableSeat(flightRequest.getAvailableSeat());
        newflight.setDate(flightRequest.getDate());
        newflight.setTime(flightRequest.getTime());

        flightRepository.save(newflight);

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

    public FlightsAvailable getAvailableFlight(String fromCity, String toCity, String date){
        LocalDate flightDate = LocalDate.parse(date);
        List<Flight> flightList= flightRepository.findByFromCityAndToCityAndDate(fromCity, toCity, flightDate);
        FlightsAvailable flightsAvailable=new FlightsAvailable();
        flightsAvailable.setFlights(flightList);
        if(flightList.isEmpty()){
            flightsAvailable.setMessage("Not Available");
        }else{
            flightsAvailable.setMessage("Available");
        }
        return flightsAvailable;
    }

    public boolean seatBook(int numberOfSeat,String flightId){
        Flight flight=getFlightById(flightId);
        if(numberOfSeat > flight.getAvailableSeat()){
            throw new CustomException("Booking can't proceed as Seat Not available for flight :: "+flightId,"setNotAvailable");
        }
        flight.setAvailableSeat(flight.getAvailableSeat()-numberOfSeat);
        flightRepository.save(flight);
        return true;
    }

}
