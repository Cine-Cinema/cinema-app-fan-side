package com.cinemaapp.cinema.service;

import com.cinemaapp.cinema.dto.MovieSearchResponse;
import com.cinemaapp.cinema.entity.Movie;
import com.cinemaapp.cinema.entity.Showtime;
import com.cinemaapp.cinema.repository.MovieRepository;
import com.cinemaapp.cinema.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;

    public MovieService(
            MovieRepository movieRepository,
            ShowtimeRepository showtimeRepository
    ) {
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
    }

    // Search movies by title and/or date
    public List<MovieSearchResponse> searchMovies(String title, LocalDate date) {

        // Convert an empty title into an empty string
        if (title == null || title.isBlank()) {
            title = "";
        } else {
            title = title.trim();
        }

        // Get matching movies from the movies table
        List<Movie> movies;

        if (title.isEmpty()) {
            movies = movieRepository.findAll();
        } else {
            movies = movieRepository.findByTitleContainingIgnoreCase(title);
        }

        // Store the movie responses
        List<MovieSearchResponse> movieResponses = new ArrayList<>();

        for (Movie movie : movies) {

            // Get active showtimes for this movie
            List<Showtime> showtimes;

            if (date != null) {
                showtimes = showtimeRepository
                        .findByMovieIdAndShowDateAndActiveTrueOrderByShowTimeAsc(
                                movie.getId(),
                                date
                        );
            } else {
                showtimes = showtimeRepository
                        .findByMovieIdAndActiveTrueOrderByShowDateAscShowTimeAsc(
                                movie.getId()
                        );
            }

            // If a date was provided, skip movies without screenings on that date
            if (date != null && showtimes.isEmpty()) {
                continue;
            }

            // Convert showtimes into ShowtimeInfo objects
            List<MovieSearchResponse.ShowtimeInfo> movieShowtimes =
                    new ArrayList<>();

            for (Showtime showtime : showtimes) {

                movieShowtimes.add(
                        new MovieSearchResponse.ShowtimeInfo(
                                showtime.getId(),
                                showtime.getShowDate(),
                                showtime.getShowTime(),
                                showtime.getPrice(),
                                showtime.getTotalSeats(),
                                showtime.getSeatsPerRow()
                        )
                );
            }

            // Create the movie response using the Movie entity
            MovieSearchResponse movieResponse = new MovieSearchResponse(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDescription(),
                    movie.getPosterUrl(),
                    movieShowtimes
            );

            movieResponses.add(movieResponse);
        }

        return movieResponses;
    }


    // Get details of a particular movie
    public MovieSearchResponse getMovieDetails(Long movieId) {

        // Find the movie using its local database ID
        Movie movie = movieRepository.findById(movieId).orElse(null);

        // Check whether the movie exists
        if (movie == null) {
            return null;
        }

        // Get today's date
        LocalDate today = LocalDate.now();

        // Calculate the last date of the 30-day period
        LocalDate lastDate = today.plusDays(29);

        // Get all active showtimes for this movie
        List<Showtime> allShowtimes =
                showtimeRepository
                        .findByMovieIdAndActiveTrueOrderByShowDateAscShowTimeAsc(
                                movieId
                        );

        // Store showtimes available within the next 30 days
        List<MovieSearchResponse.ShowtimeInfo> movieShowtimes =
                new ArrayList<>();

        for (Showtime showtime : allShowtimes) {

            // Include only showtimes within the next 30 days
            if (!showtime.getShowDate().isBefore(today)
                    && !showtime.getShowDate().isAfter(lastDate)) {

                movieShowtimes.add(
                        new MovieSearchResponse.ShowtimeInfo(
                                showtime.getId(),
                                showtime.getShowDate(),
                                showtime.getShowTime(),
                                showtime.getPrice(),
                                showtime.getTotalSeats(),
                                showtime.getSeatsPerRow()
                        )
                );
            }
        }

        // Return movie information and its available showtimes
        return new MovieSearchResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getPosterUrl(),
                movieShowtimes
        );
    }
}
