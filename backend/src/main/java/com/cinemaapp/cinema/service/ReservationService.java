package com.cinemaapp.cinema.service;

import com.cinemaapp.cinema.dto.reservation.MockReservationResponse;
import com.cinemaapp.cinema.dto.reservation.ReservationRequest;
import com.cinemaapp.cinema.dto.reservation.ReservationResponse;

import com.cinemaapp.cinema.entity.Fan;
import com.cinemaapp.cinema.entity.Reservation;
import com.cinemaapp.cinema.entity.ReservationSeat;
import com.cinemaapp.cinema.entity.Showtime;

import com.cinemaapp.cinema.repository.FanRepository;
import com.cinemaapp.cinema.repository.ReservationRepository;
import com.cinemaapp.cinema.repository.ReservationSeatRepository;
import com.cinemaapp.cinema.repository.ShowtimeRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReservationService {

    private final FanRepository fanRepository;
    private final ShowtimeRepository showtimeRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final MockCinemaService mockCinemaService;

    public ReservationService(
            FanRepository fanRepository,
            ShowtimeRepository showtimeRepository,
            ReservationRepository reservationRepository,
            ReservationSeatRepository reservationSeatRepository,
            MockCinemaService mockCinemaService
    ) {
        this.fanRepository = fanRepository;
        this.showtimeRepository = showtimeRepository;
        this.reservationRepository = reservationRepository;
        this.reservationSeatRepository = reservationSeatRepository;
        this.mockCinemaService = mockCinemaService;
    }

    @Transactional
    public ReservationResponse makeReservation(
            String email,
            ReservationRequest request
    ) {

        // 1. Identify the logged-in fan
        Fan fan = fanRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fan account not found."
                ));

        // 2. Retrieve the requested showtime
        Showtime showtime = showtimeRepository.findById(request.getShowtimeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Showtime not found."
                ));

        // 3. Validate the selected seats
        List<Integer> seatNumbers = request.getSeatNumbers();

        if (seatNumbers == null || seatNumbers.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please select at least one seat."
            );
        }

        // Prevent duplicate seat numbers in the same request
        Set<Integer> uniqueSeats = new HashSet<>(seatNumbers);

        if (uniqueSeats.size() != seatNumbers.size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Duplicate seat numbers are not allowed."
            );
        }

        // Check whether seat numbers are within the showtime's seat range
        for (Integer seatNumber : seatNumbers) {

            if (seatNumber == null ||
                    seatNumber < 1 ||
                    seatNumber > showtime.getTotalSeats()) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid seat number: " + seatNumber
                );
            }
        }

        // 4. Calculate the total amount on the backend
        BigDecimal totalAmount = showtime.getPrice()
                .multiply(BigDecimal.valueOf(seatNumbers.size()));

        // 5. Ask the mock cinema service for its decision
        MockReservationResponse cinemaResponse =
                mockCinemaService.checkReservation();

        // 6. If rejected, save the rejected reservation
        if (!cinemaResponse.isAccepted()) {

            Reservation rejectedReservation = new Reservation(
                    fan,
                    showtime,
                    "REJECTED",
                    cinemaResponse.getMessage(),
                    BigDecimal.ZERO
            );

            Reservation savedReservation =
                    reservationRepository.save(rejectedReservation);

            return new ReservationResponse(
                    false,
                    cinemaResponse.getMessage(),
                    savedReservation.getId(),
                    showtime.getMovieId(),
                    showtime.getId(),
                    showtime.getShowDate(),
                    showtime.getShowTime(),
                    List.of(),
                    BigDecimal.ZERO
            );
        }

        // 7. Create and save the reservation
        Reservation reservation = new Reservation(
                fan,
                showtime,
                "CONFIRMED",
                null,
                totalAmount
        );

        Reservation savedReservation =
                reservationRepository.save(reservation);

        // 8. Save the selected seats
        List<ReservationSeat> reservationSeats = seatNumbers.stream()
                .map(seatNumber ->
                        new ReservationSeat(savedReservation, seatNumber)
                )
                .toList();

        reservationSeatRepository.saveAll(reservationSeats);

        // 9. Return the successful reservation response
        return new ReservationResponse(
                true,
                "Your reservation was successful!",
                savedReservation.getId(),
                showtime.getMovieId(),
                showtime.getId(),
                showtime.getShowDate(),
                showtime.getShowTime(),
                seatNumbers,
                totalAmount
        );
    }

    // Retrieve reserved seats for a particular showtime
    public List<Integer> getReservedSeats(Long showtimeId) {

        if (!showtimeRepository.existsById(showtimeId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Showtime not found."
            );
        }

        return reservationSeatRepository
                .findReservedSeatsByShowtimeId(showtimeId);
    }
}