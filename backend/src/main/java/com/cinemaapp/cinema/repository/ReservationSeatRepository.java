package com.cinemaapp.cinema.repository;

import com.cinemaapp.cinema.entity.ReservationSeat;
import com.cinemaapp.cinema.entity.ReservationSeatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, ReservationSeatId> {

}