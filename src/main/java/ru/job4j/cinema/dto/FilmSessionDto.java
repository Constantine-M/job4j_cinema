package ru.job4j.cinema.dto;

import java.time.LocalDateTime;

public class FilmSessionDto {

    private final int id;

    private final LocalDateTime startTime;

    private final LocalDateTime endTime;

    private final int price;

    private final String filmName;

    private FilmSessionDto(FilmSessionDtoBuilder builder) {
        this.id = builder.id;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.price = builder.price;
        this.filmName = builder.filmName;
    }

    public int id() {
        return id;
    }

    public LocalDateTime startTime() {
        return startTime;
    }

    public LocalDateTime endTime() {
        return endTime;
    }

    public int price() {
        return price;
    }

    public String filmName() {
        return filmName;
    }

    /**
     * Данный класс позволяет создавать
     * различные комбинации нужного
     * объекта. То есть, во время создания
     * объекта мы можем указать только
     * те поля, которые нам требуются.
     *
     * Еще данный способ удобен тем, что
     * нам не придется задумываться над
     * тем, в каком порядке прописывать
     * параметры объекта во время его создания.
     *
     * Здесь мы применили шаблон
     * проектирования "Строитель" (в народе
     * "билдер").
     */
    public static class FilmSessionDtoBuilder {

        private int id;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private int price;
        private String filmName;

        public FilmSessionDtoBuilder() {
            super();
        }

        public FilmSessionDtoBuilder id(int id) {
            this.id = id;
            return this;
        }

        public FilmSessionDtoBuilder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public FilmSessionDtoBuilder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public FilmSessionDtoBuilder price(int price) {
            this.price = price;
            return this;
        }
        public FilmSessionDtoBuilder filmName(String filmName) {
            this.filmName = filmName;
            return this;
        }

        public FilmSessionDto build() {
            return new FilmSessionDto(this);
        }
    }
}
