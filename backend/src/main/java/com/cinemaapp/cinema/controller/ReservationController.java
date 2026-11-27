package com.cinemaapp.cinema.controller;

import com.cinemaapp.cinema.dto.reservation.ReservationRequest;
import com.cinemaapp.cinema.dto.reservation.ReservationResponse;
import com.cinemaapp.cinema.service.ReservationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Create a reservation
    @PostMapping
    public ResponseEntity<ReservationResponse> makeReservation(
            @RequestBody ReservationRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        ReservationResponse response =
                reservationService.makeReservation(email, request);

        return ResponseEntity.ok(response);
    }

    // Get reserved seats for a particular showtime
    @GetMapping("/showtime/{showtimeId}/seats")
    public ResponseEntity<Map<String, Object>> getReservedSeats(
            @PathVariable Long showtimeId
    ) {

        List<Integer> reservedSeats =
                reservationService.getReservedSeats(showtimeId);

        Map<String, Object> response = Map.of(
                "showtimeId", showtimeId,
                "reservedSeats", reservedSeats
        );

        return ResponseEntity.ok(response);
    }
}