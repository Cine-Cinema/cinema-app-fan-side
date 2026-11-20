package com.cinemaapp.cinema.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ReservationSeatId implements Serializable {

    private UUID reservation;
    private Integer seatNumber;


    public ReservationSeatId() {
    }

    public ReservationSeatId(UUID reservation, Integer seatNumber) {
        this.reservation = reservation;
        this.seatNumber = seatNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationSeatId that)) return false;

        return Objects.equals(reservation, that.reservation)
                && Objects.equals(seatNumber, that.seatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation, seatNumber);
    }
}