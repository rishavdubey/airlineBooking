package com.axio.airlinereservation.service;

import com.axio.airlinereservation.common.UniqueIdGenerator;
import com.axio.airlinereservation.entity.Booking;
import com.axio.airlinereservation.entity.Flight;
import com.axio.airlinereservation.entity.Reservation;
import com.axio.airlinereservation.repository.BookingRepository;
import com.axio.airlinereservation.response.BookingResponse;
import com.axio.airlinereservation.response.FlightDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private FlightService flightService;
    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    public BookingResponse confirmBooking(String reservationId){
        Reservation reservation= reservationService.getReservationById(reservationId);
        Flight flight= flightService.getFlightById(reservation.getFlightId());

        FlightDetails flightDetails=new FlightDetails();
        flightDetails.setFlightCode(flight.getId());
        flightDetails.setFlightName(flight.getName());
        flightDetails.setFrom(flight.getFromCity());
        flightDetails.setTo(flight.getToCity());

        BookingResponse bookingResponse=new BookingResponse();
        bookingResponse.setStatus("confirmed");
        bookingResponse.setFlightDetails(flightDetails);

        Optional<Booking> bookingOptional = bookingRepository.findByReservationId(reservationId);

        if (bookingOptional.isEmpty()) {
            Booking newBooking=new Booking();
            newBooking.setBookingId("BOK_"+uniqueIdGenerator.generateUniqueId());
            newBooking.setReservationId(reservationId);
            newBooking=bookingRepository.save(newBooking);
            bookingResponse.setBookingId(newBooking.getBookingId());
            bookingResponse.setMessage("Your Booking is Confirmed Now!");
            return bookingResponse;
        }
        bookingResponse.setBookingId(bookingOptional.get().getBookingId());
        bookingResponse.setMessage("Booking is already Confirmed...");
        return bookingResponse;

    }
}
