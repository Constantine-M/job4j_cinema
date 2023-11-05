package ru.job4j.cinema.model;

import java.util.Map;

public class Hall {

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
            "row_count", "rowCount",
            "place_count", "placeCount",
            "description", "description"
    );

    private int id;

    private String name;

    private int rowCount;

    private int placeCount;

    private String description;

    public Hall(int id, String name, int rowCount, int placeCount, String description) {
        this.id = id;
        this.name = name;
        this.rowCount = rowCount;
        this.placeCount = placeCount;
        this.description = description;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int rowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int placeCount() {
        return placeCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hall hall)) {
            return false;
        }

        return id == hall.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
