package com.axio.airlinereservation.request.flight;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FlightRequest {

    @NotBlank(message = "ID cannot be blank")
    private String id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Positive(message = "Fare must be a positive number")
    private double fare;

    @NotBlank(message = "From city cannot be blank")
    private String fromCity;

    @NotBlank(message = "To city cannot be blank")
    private String toCity;

    @Min(value = 1, message = "Available seats must be at least 1")
    @NotNull(message = "Available seats is mandatory")
    private Integer availableSeat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public Integer getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(Integer availableSeat) {
        this.availableSeat = availableSeat;
    }
}
