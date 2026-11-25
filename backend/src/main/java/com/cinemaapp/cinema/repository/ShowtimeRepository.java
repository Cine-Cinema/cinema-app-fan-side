package com.cinemaapp.cinema.repository;

import com.cinemaapp.cinema.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    @Query(value = """
        SELECT *
        FROM showtimes s
        WHERE s.active = true
        AND (
            CAST(:title AS TEXT) = ''
            OR LOWER(s.title) LIKE LOWER(CONCAT('%', CAST(:title AS TEXT), '%'))
        )
        AND (
            CAST(:date AS DATE) IS NULL
            OR s.show_date = CAST(:date AS DATE)
        )
        ORDER BY s.show_date ASC, s.show_time ASC
        """, nativeQuery = true)
    List<Showtime> searchActiveShowtimes(
            @Param("title") String title,
            @Param("date") LocalDate date
    );
}