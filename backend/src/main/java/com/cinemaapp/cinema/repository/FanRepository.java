package com.cinemaapp.cinema.repository;

import com.cinemaapp.cinema.entity.Fan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FanRepository extends JpaRepository<Fan, Long> {
    Optional<Fan> findByEmail(String email);

    boolean existsByEmail(String email);
}