package com.cinemaapp.cinema.repository;

import com.cinemaapp.cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Search movies by title (case-insensitive)
    List<Movie> findByTitleContainingIgnoreCase(String title);

    // Find a movie using its cinema-side movie ID
    Movie findByCinemaMovieId(Long cinemaMovieId);
}
