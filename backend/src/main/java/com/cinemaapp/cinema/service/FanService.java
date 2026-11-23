package com.cinemaapp.cinema.service;

import com.cinemaapp.cinema.dto.FanAccountResponse;
import com.cinemaapp.cinema.dto.UpdateFanRequest;
import com.cinemaapp.cinema.entity.Fan;
import com.cinemaapp.cinema.repository.FanRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FanService {

    private final FanRepository fanRepository;

    public FanService(FanRepository fanRepository) {
        this.fanRepository = fanRepository;
    }

    public FanAccountResponse getFanAccount(String email) {
        Fan fan = fanRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Fan not found"));

        return new FanAccountResponse(
                fan.getId(),
                fan.getEmail(),
                fan.getFullName(),
                fan.getPhone()
        );
    }

    @Transactional
    public FanAccountResponse updateFanAccount(
            String email,
            UpdateFanRequest request) {

        Fan fan = fanRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Fan not found"));

        fan.setFullName(request.getFullName());
        fan.setPhone(request.getPhone());

        Fan updatedFan = fanRepository.save(fan);

        return new FanAccountResponse(
                updatedFan.getId(),
                updatedFan.getEmail(),
                updatedFan.getFullName(),
                updatedFan.getPhone()
        );
    }
}