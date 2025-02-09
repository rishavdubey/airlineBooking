package com.axio.airlinereservation.common;

import org.springframework.stereotype.Component;

@Component
public class UniqueIdGenerator {

    public String generateUniqueId() {
        return String.valueOf(System.currentTimeMillis());
    }

}

