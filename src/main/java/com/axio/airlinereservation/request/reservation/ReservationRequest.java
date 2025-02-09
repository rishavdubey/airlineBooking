package com.axio.airlinereservation.request.reservation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReservationRequest {

    @NotNull(message = "Number of seats is mandatory")
    @Min(value = 1, message = "Number of seats must be at least 1")
    private Integer numberOfSeat;

    public Integer getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(Integer numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }
}

