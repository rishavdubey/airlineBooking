package com.axio.airlineReservation.service;

import com.axio.airlineReservation.Exception.CustomException;
import com.axio.airlineReservation.common.UniqueIdGenerator;
import com.axio.airlineReservation.entity.Flight;
import com.axio.airlineReservation.entity.Reservation;
import com.axio.airlineReservation.entity.UserDetails;
import com.axio.airlineReservation.repository.ReservationRepository;
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
