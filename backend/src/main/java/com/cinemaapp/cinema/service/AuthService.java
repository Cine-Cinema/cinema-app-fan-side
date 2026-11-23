package com.cinemaapp.cinema.service;

import com.cinemaapp.cinema.dto.RegisterRequest;
import com.cinemaapp.cinema.entity.Fan;
import com.cinemaapp.cinema.repository.FanRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cinemaapp.cinema.security.JwtService;
import com.cinemaapp.cinema.dto.LoginRequest;
import com.cinemaapp.cinema.dto.AuthResponse;

@Service
public class AuthService {
    private final FanRepository fanRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(FanRepository fanRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.fanRepository = fanRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Fan Registration
    public Fan registerFan(RegisterRequest request) {

        // Check whether the email is already registered
        if (fanRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered!");
        }

        // Hash the password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Create a new Fan object
        Fan fan = new Fan(
                request.getEmail(),
                hashedPassword,
                request.getFullName(),
                request.getPhone()
        );

        // Save the fan in the database
        return fanRepository.save(fan);
    }

    // Fan Login
    public AuthResponse loginFan(LoginRequest request) {

        // Find the fan using email
        Fan fan = fanRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Verify the entered password
        if (!passwordEncoder.matches(request.getPassword(), fan.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtService.generateToken(fan.getEmail());

        // Return authentication response
        return new AuthResponse(
                token,
                fan.getEmail(),
                fan.getFullName()
        );
    }
}
