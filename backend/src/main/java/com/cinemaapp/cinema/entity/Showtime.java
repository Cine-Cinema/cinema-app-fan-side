package com.cinemaapp.cinema.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "showtimes")
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "poster_url", length = 500)
    private String posterUrl;

    @Column(name = "cinema_name", length = 150)
    private String cinemaName;

    @Column(name = "show_date")
    private LocalDate showDate;

    @Column(name = "show_time")
    private LocalTime showTime;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @Column(name = "seats_per_row")
    private Integer seatsPerRow;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "reserved_seats", columnDefinition = "integer[]")
    private Integer[] reservedSeats;

    @Column(name = "active")
    private Boolean active;

    public Showtime(){
    }

    public Showtime(Long movieId, String title, String description, String posterUrl, String cinemaName, LocalDate showDate, LocalTime showTime, BigDecimal price, Integer totalSeats, Integer seatsPerRow, Integer[] reservedSeats, Boolean active) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.cinemaName = cinemaName;
        this.showDate = showDate;
        this.showTime = showTime;
        this.price = price;
        this.totalSeats = totalSeats;
        this.seatsPerRow = seatsPerRow;
        this.reservedSeats = reservedSeats;
        this.active = active;
    }

    public Long getId() {
        return id;
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

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public Integer getSeatsPerRow() {
        return seatsPerRow;
    }

    public Integer[] getReservedSeats() {
        return reservedSeats;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public void setSeatsPerRow(Integer seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public void setReservedSeats(Integer[] reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
