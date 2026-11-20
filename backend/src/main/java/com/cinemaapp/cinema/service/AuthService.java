package com.cinemaapp.cinema.service;

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
}
