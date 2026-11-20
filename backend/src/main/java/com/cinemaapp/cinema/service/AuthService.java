package com.cinemaapp.cinema.service;

import com.cinemaapp.cinema.dto.RegisterRequest;
import com.cinemaapp.cinema.entity.Fan;
import com.cinemaapp.cinema.repository.FanRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final FanRepository fanRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(FanRepository fanRepository, PasswordEncoder passwordEncoder) {
        this.fanRepository = fanRepository;
        this.passwordEncoder = passwordEncoder;
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
}
