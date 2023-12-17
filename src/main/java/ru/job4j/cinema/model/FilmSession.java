package ru.job4j.cinema.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Большая часть кода модели
 * (геттеры, сеттеры, Equals and HashCode)
 * была сгенерирована с помощью
 * бибилиотеки Lombok.
 *
 * Это было сделано, чтобы код
 * помещался на 1 страницу для
 * удобства чтения и, одновременно
 * с этим, не был перегружен
 * аннотациями.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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

    @EqualsAndHashCode.Include
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
}
