package com.cinemaapp.cinema.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation_seats")
@IdClass(ReservationSeatId.class)
public class ReservationSeat {

    @Id
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Id
    @Column(name = "seat_number")
    private Integer seatNumber;

    public ReservationSeat() {
    }

    public ReservationSeat(Reservation reservation, Integer seatNumber) {
        this.reservation = reservation;
        this.seatNumber = seatNumber;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}