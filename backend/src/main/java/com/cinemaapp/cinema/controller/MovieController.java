package com.cinemaapp.cinema.controller;

import com.cinemaapp.cinema.dto.MovieSearchResponse;
import com.cinemaapp.cinema.service.MovieService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieSearchResponse>> searchMovies(

            @RequestParam(required = false)
            String title,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date

    ) {

        List<MovieSearchResponse> movies =
                movieService.searchMovies(title, date);

        return ResponseEntity.ok(movies);
    }
}