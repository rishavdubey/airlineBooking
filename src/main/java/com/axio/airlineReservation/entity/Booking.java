package com.axio.airlineReservation.entity;

import jakarta.persistence.*;

@Entity
public class Booking {
    @Id
    private String bookingId;

    private String reservationId;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
}
