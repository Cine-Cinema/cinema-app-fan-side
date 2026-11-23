package com.cinemaapp.cinema.controller;

import com.cinemaapp.cinema.dto.FanAccountResponse;
import com.cinemaapp.cinema.dto.UpdateFanRequest;
import com.cinemaapp.cinema.service.FanService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fans")
public class FanController {

    private final FanService fanService;

    public FanController(FanService fanService) {
        this.fanService = fanService;
    }

    @GetMapping("/me")
    public ResponseEntity<FanAccountResponse> getMyAccount(
            Authentication authentication) {

        String email = authentication.getName();     // The email comes from the JWT authentication filter. The frontend does not get to choose which account to update.
        FanAccountResponse response = fanService.getFanAccount(email);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<FanAccountResponse> updateMyAccount(
            Authentication authentication,
            @RequestBody UpdateFanRequest request) {

        String email = authentication.getName();    // The email comes from the JWT authentication filter. The frontend does not get to choose which account to update.

        FanAccountResponse response =
                fanService.updateFanAccount(email, request);

        return ResponseEntity.ok(response);
    }
}