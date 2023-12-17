package ru.job4j.cinema.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
public class Film {

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
            "name", "name",
            "description", "description",
            "\"year\"", "year",
            "genre_id", "genreId",
            "minimal_age", "minimalAge",
            "duration_in_minutes", "durationInMinutes",
            "file_id", "fileId"
    );

    @EqualsAndHashCode.Include
    private int id;

    private String name;

    private String description;

    /**
     * Год выпуска фильма.
     */
    private int year;

    private int genreId;

    private int minimalAge;

    private int durationInMinutes;

    private int fileId;

    public Film(int id, String name, String description, int year, int genreId, int minimalAge, int durationInMinutes, int fileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.genreId = genreId;
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
        this.fileId = fileId;
    }
}
