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

    public List<MovieSearchResponse> searchMovies(String title, LocalDate date) {

        // Convert an empty title into null
        if (title == null || title.isBlank()) {
            title = "";
        } else {
            title = title.trim();
        }

        // Remove unnecessary spaces from the title
        if (title != null) {
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
                                showtime.getPrice()
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
                                showtime.getPrice()
                        )
                );
            }
        }

        // Convert the map values into a list
        return new ArrayList<>(movieMap.values());
    }
}