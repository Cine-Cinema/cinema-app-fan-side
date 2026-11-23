package com.cinemaapp.cinema.controller;

import com.cinemaapp.cinema.dto.AuthResponse;
import com.cinemaapp.cinema.dto.LoginRequest;
import com.cinemaapp.cinema.dto.RegisterRequest;
import com.cinemaapp.cinema.entity.Fan;
import com.cinemaapp.cinema.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Fan Registration
    @PostMapping("/register")
    public ResponseEntity<String> registerFan(@RequestBody RegisterRequest request) {

        authService.registerFan(request);

        return ResponseEntity.ok("Fan registered successfully!");
    }

    // Fan Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginFan(@RequestBody LoginRequest request) {

        AuthResponse response = authService.loginFan(request);

        return ResponseEntity.ok(response);
    }
}