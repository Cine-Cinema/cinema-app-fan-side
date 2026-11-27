package com.cinemaapp.cinema.service;

import com.cinemaapp.cinema.dto.reservation.MockReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MockCinemaService {

    @Value("${mock.cinema.accept-reservations:true}")
    private boolean acceptReservations;

    public MockReservationResponse checkReservation() {

        if (acceptReservations) {
            return new MockReservationResponse(
                    true,
                    "Reservation accepted successfully."
            );
        }

        return new MockReservationResponse(
                false,
                "The selected seats are no longer available."
        );
    }
}