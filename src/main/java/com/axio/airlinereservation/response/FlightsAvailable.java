package com.axio.airlinereservation.response;

import com.axio.airlinereservation.entity.Flight;

import java.util.List;

public class FlightsAvailable {
    private String message;
    private List<Flight> flights;

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
