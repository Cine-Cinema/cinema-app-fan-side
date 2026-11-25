package com.cinemaapp.cinema.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MovieSearchResponse {

    private Long movieId;
    private String title;
    private String description;
    private String posterUrl;
    private List<ShowtimeInfo> showtimes;

    public MovieSearchResponse(
            Long movieId,
            String title,
            String description,
            String posterUrl,
            List<ShowtimeInfo> showtimes
    ) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.showtimes = showtimes;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public List<ShowtimeInfo> getShowtimes() {
        return showtimes;
    }

    // Information about an individual screening
    public static class ShowtimeInfo {

        private Long showtimeId;
        private String cinemaName;
        private LocalDate showDate;
        private LocalTime showTime;
        private BigDecimal price;

        public ShowtimeInfo(
                Long showtimeId,
                String cinemaName,
                LocalDate showDate,
                LocalTime showTime,
                BigDecimal price
        ) {
            this.showtimeId = showtimeId;
            this.cinemaName = cinemaName;
            this.showDate = showDate;
            this.showTime = showTime;
            this.price = price;
        }

        public Long getShowtimeId() {
            return showtimeId;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public LocalDate getShowDate() {
            return showDate;
        }

        public LocalTime getShowTime() {
            return showTime;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}