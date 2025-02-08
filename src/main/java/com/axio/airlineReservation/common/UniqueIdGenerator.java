package com.axio.airlineReservation.common;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueIdGenerator {

    public String generateUniqueId() {

//        return UUID.randomUUID().toString();
        return String.valueOf(System.currentTimeMillis());
    }

}

