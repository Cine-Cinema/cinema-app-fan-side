package com.cinemaapp.cinema.dto.reservation;

public class MockReservationResponse {

    private boolean accepted;
    private String message;

    public MockReservationResponse() {
    }

    public MockReservationResponse(boolean accepted, String message) {
        this.accepted = accepted;
        this.message = message;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}