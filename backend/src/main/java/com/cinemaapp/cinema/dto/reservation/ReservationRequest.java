// React will send,
// {
//    "showtimeId": 1,
//    "seatNumbers": [5, 6, 7]
// }

package com.cinemaapp.cinema.dto.reservation;

import java.util.List;

public class ReservationRequest {

    private Long showtimeId;
    private List<Integer> seatNumbers;

    public ReservationRequest() {
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    public List<Integer> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<Integer> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }
}