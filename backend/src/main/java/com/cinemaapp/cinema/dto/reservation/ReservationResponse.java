// An accepted reservation will return something like:
// {
//    "success": true,
//    "message": "Your reservation was successful!",
//    "bookingReference": "a1234567-1234-1234-1234-123456789012",
//    "movieId": 1,
//    "showtimeId": 1,
//    "showDate": "2026-11-28",
//    "showTime": "18:30:00",
//    "seatNumbers": [5, 6, 7],
//    "totalAmount": 4500.00
// }
// A rejected reservation will return success: false, the rejection message, and no booking reference.

package com.cinemaapp.cinema.dto.reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class ReservationResponse {

    private boolean success;
    private String message;

    private UUID bookingReference;

    private Long movieId;
    private Long showtimeId;

    private LocalDate showDate;
    private LocalTime showTime;

    private List<Integer> seatNumbers;

    private BigDecimal totalAmount;

    public ReservationResponse() {
    }

    public ReservationResponse(
            boolean success,
            String message,
            UUID bookingReference,
            Long movieId,
            Long showtimeId,
            LocalDate showDate,
            LocalTime showTime,
            List<Integer> seatNumbers,
            BigDecimal totalAmount
    ) {
        this.success = success;
        this.message = message;
        this.bookingReference = bookingReference;
        this.movieId = movieId;
        this.showtimeId = showtimeId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.seatNumbers = seatNumbers;
        this.totalAmount = totalAmount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(UUID bookingReference) {
        this.bookingReference = bookingReference;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }

    public List<Integer> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<Integer> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}