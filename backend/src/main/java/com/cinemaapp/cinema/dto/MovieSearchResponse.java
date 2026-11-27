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

    public MovieSearchResponse() {
    }

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

    // Getters and Setters

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<ShowtimeInfo> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<ShowtimeInfo> showtimes) {
        this.showtimes = showtimes;
    }


    // Inner class for showtime information
    public static class ShowtimeInfo {

        private Long showtimeId;
        private LocalDate showDate;
        private LocalTime showTime;
        private BigDecimal price;
        private Integer totalSeats;
        private Integer seatsPerRow;

        public ShowtimeInfo() {
        }

        public ShowtimeInfo(
                Long showtimeId,
                LocalDate showDate,
                LocalTime showTime,
                BigDecimal price,
                Integer totalSeats,
                Integer seatsPerRow
        ) {
            this.showtimeId = showtimeId;
            this.showDate = showDate;
            this.showTime = showTime;
            this.price = price;
            this.totalSeats = totalSeats;
            this.seatsPerRow = seatsPerRow;
        }

        // Getters and Setters

        public Long getShowtimeId() {
            return showtimeId;
        }

        public void setShowtimeId(Long showtimeId) {
            this.showtimeId = showtimeId;
        }

        public LocalDate getShowDate() {
            return showDate;
        }

        public void setShowDate(LocalDate showDate) {
            this.showDate = showDate;
        }

        public LocalTime getShowTime() {
            return showTime;
        }

        public void setShowTime(LocalTime showTime) {
            this.showTime = showTime;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Integer getTotalSeats() {
            return totalSeats;
        }

        public void setTotalSeats(Integer totalSeats) {
            this.totalSeats = totalSeats;
        }

        public Integer getSeatsPerRow() {
            return seatsPerRow;
        }

        public void setSeatsPerRow(Integer seatsPerRow) {
            this.seatsPerRow = seatsPerRow;
        }
    }
}