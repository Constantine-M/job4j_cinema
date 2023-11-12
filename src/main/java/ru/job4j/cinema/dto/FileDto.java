package ru.job4j.cinema.dto;

/**
 * Данный класс описывает файл-объект
 * для передачи данных.
 */
public class FileDto {
    private String name;

    /**
     * Данное поле описывает содержимое
     * файла, который мы будем передавать.
     *
     * Доменная модель хранит путь,
     * а DTO хранит содержимое.
     */
    private byte[] content;

    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] content() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
