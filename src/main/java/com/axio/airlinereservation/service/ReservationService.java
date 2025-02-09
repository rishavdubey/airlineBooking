package com.axio.airlinereservation.service;

import com.axio.airlinereservation.exception.CustomException;
import com.axio.airlinereservation.common.UniqueIdGenerator;
import com.axio.airlinereservation.entity.Flight;
import com.axio.airlinereservation.entity.Reservation;
import com.axio.airlinereservation.entity.UserDetails;
import com.axio.airlinereservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FlightService flightService;
    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    public Reservation createReservation(String userId,String flightId){
        UserDetails userDetails=userService.getUserById(userId);
        Flight flightDetails=flightService.getFlightById(flightId);
        Reservation reservation=new Reservation();
        reservation.setUserEmail(userDetails.getEmail());
        reservation.setUserName(userDetails.getName());
        reservation.setFlightId(flightDetails.getId());
        reservation.setReservationId("RES_"+uniqueIdGenerator.generateUniqueId());
        reservation=reservationRepository.save(reservation);
        return reservation;
    }

    public Reservation getReservationById(String reservationId){
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation == null) {
            throw new CustomException("Reservation does not exist for reservationId :: "+reservationId);
        }
        return reservation;
    }
}
