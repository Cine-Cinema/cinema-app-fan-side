package com.cinemaapp.cinema.dto;

public class FanAccountResponse {

    private Long id;
    private String email;
    private String fullName;
    private String phone;

    public FanAccountResponse(Long id, String email, String fullName, String phone) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }
}