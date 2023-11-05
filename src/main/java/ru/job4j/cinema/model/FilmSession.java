package ru.job4j.cinema.model;

import java.time.LocalDateTime;
import java.util.Map;

public class FilmSession {

    /**
     * В данном случае ситуация
     * с полями немного сложнее, поэтому
     * нам нужно написать маппинг в
     * виде мапы COLUMN_MAPPING, ключи
     * которой это столбцы из БД,
     * а значения названия полей.
     */
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmId",
            "halls_id", "hallId",
            "start_time", "startTime",
            "end_time", "endTime",
            "price", "price"
    );

    private int id;

    private int filmId;

    private int hallId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int price;

    public FilmSession(int id, int filmId, int hallId, LocalDateTime startTime, LocalDateTime endTime, int price) {
        this.id = id;
        this.filmId = filmId;
        this.hallId = hallId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int filmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int hallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public LocalDateTime startTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime endTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int price() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilmSession that)) {
            return false;
        }

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
