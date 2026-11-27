package com.cinemaapp.cinema.repository;

import com.cinemaapp.cinema.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    // Get all active showtimes for a particular movie
    List<Showtime> findByMovieIdAndActiveTrueOrderByShowDateAscShowTimeAsc(
            Long movieId
    );

    // Get all active showtimes for a particular movie on a specific date
    List<Showtime> findByMovieIdAndShowDateAndActiveTrueOrderByShowTimeAsc(
            Long movieId,
            LocalDate showDate
    );

    // Get all active showtimes on a specific date
    List<Showtime> findByShowDateAndActiveTrueOrderByShowTimeAsc(
            LocalDate showDate
    );
}