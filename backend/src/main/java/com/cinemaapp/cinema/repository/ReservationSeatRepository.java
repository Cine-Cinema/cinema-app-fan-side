package com.cinemaapp.cinema.repository;

import com.cinemaapp.cinema.entity.ReservationSeat;
import com.cinemaapp.cinema.entity.ReservationSeatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationSeatRepository
        extends JpaRepository<ReservationSeat, ReservationSeatId> {

    // Get all reserved seat numbers for a particular showtime
    @Query("""
        SELECT rs.seatNumber
        FROM ReservationSeat rs
        WHERE rs.reservation.showtime.id = :showtimeId
        AND rs.reservation.status = 'CONFIRMED'
    """)
    List<Integer> findReservedSeatsByShowtimeId(
            @Param("showtimeId") Long showtimeId
    );
}