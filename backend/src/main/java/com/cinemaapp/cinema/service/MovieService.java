package com.cinemaapp.cinema.service;

import com.cinemaapp.cinema.dto.MovieSearchResponse;
import com.cinemaapp.cinema.entity.Showtime;
import com.cinemaapp.cinema.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieService {

    private final ShowtimeRepository showtimeRepository;

    public MovieService(ShowtimeRepository showtimeRepository) {
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

        // Get matching active showtimes from the database
        List<Showtime> showtimes =
                showtimeRepository.searchActiveShowtimes(title, date);

        // Store movies using movieId as the key
        Map<Long, MovieSearchResponse> movieMap = new LinkedHashMap<>();

        for (Showtime showtime : showtimes) {

            Long movieId = showtime.getMovieId();

            // Check whether this movie has already been added
            MovieSearchResponse movie = movieMap.get(movieId);

            if (movie == null) {

                // Create the showtime information
                List<MovieSearchResponse.ShowtimeInfo> movieShowtimes =
                        new ArrayList<>();

                movieShowtimes.add(
                        new MovieSearchResponse.ShowtimeInfo(
                                showtime.getId(),
                                showtime.getCinemaName(),
                                showtime.getShowDate(),
                                showtime.getShowTime(),
                                showtime.getPrice(),
                                showtime.getTotalSeats(),
                                showtime.getSeatsPerRow(),
                                showtime.getReservedSeats()
                        )
                );

                // Create a new movie response
                movie = new MovieSearchResponse(
                        movieId,
                        showtime.getTitle(),
                        showtime.getDescription(),
                        showtime.getPosterUrl(),
                        movieShowtimes
                );

                // Add the movie to the map
                movieMap.put(movieId, movie);

            } else {

                // Movie already exists, so add another screening
                movie.getShowtimes().add(
                        new MovieSearchResponse.ShowtimeInfo(
                                showtime.getId(),
                                showtime.getCinemaName(),
                                showtime.getShowDate(),
                                showtime.getShowTime(),
                                showtime.getPrice(),
                                showtime.getTotalSeats(),
                                showtime.getSeatsPerRow(),
                                showtime.getReservedSeats()
                        )
                );
            }
        }

        // Convert the map values into a list
        return new ArrayList<>(movieMap.values());
    }


    // Get details of a particular movie
    public MovieSearchResponse getMovieDetails(Long movieId) {

        // Get all active showtimes for this movie
        List<Showtime> allShowtimes =
                showtimeRepository
                        .findByMovieIdAndActiveTrueOrderByShowDateAscShowTimeAsc(
                                movieId
                        );

        // Check whether the movie exists
        if (allShowtimes.isEmpty()) {
            return null;
        }

        // Get movie information from the first record
        Showtime firstShowtime = allShowtimes.get(0);

        // Get today's date
        LocalDate today = LocalDate.now();

        // Calculate the last date of the 30-day period
        LocalDate lastDate = today.plusDays(29);

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
                                showtime.getCinemaName(),
                                showtime.getShowDate(),
                                showtime.getShowTime(),
                                showtime.getPrice(),
                                showtime.getTotalSeats(),
                                showtime.getSeatsPerRow(),
                                showtime.getReservedSeats()
                        )
                );
            }
        }

        // Return movie information and its available showtimes
        return new MovieSearchResponse(
                firstShowtime.getMovieId(),
                firstShowtime.getTitle(),
                firstShowtime.getDescription(),
                firstShowtime.getPosterUrl(),
                movieShowtimes
        );
    }
}