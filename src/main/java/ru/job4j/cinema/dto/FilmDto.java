package ru.job4j.cinema.dto;

/**
 * Данный класс описывает объект-фильм,
 * в котором содержатся необходимые данные
 * для отображения на веб-странице.
 */
public class FilmDto {

    private final int id;

    private final String name;

    private final String description;

    private final int year;

    private final int minimalAge;

    private final int durationInMinutes;

    private final String genre;

    private final int fileId;

    private FilmDto(FilmDtoBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.year = builder.year;
        this.minimalAge = builder.minimalAge;
        this.durationInMinutes = builder.durationInMinutes;
        this.genre = builder.genre;
        this.fileId = builder.fileId;
    }

    public int id() {
        return id;
    }


    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public int year() {
        return year;
    }

    public int minimalAge() {
        return minimalAge;
    }

    public int durationInMinutes() {
        return durationInMinutes;
    }

    public String genre() {
        return genre;
    }

    public int fileId() {
        return fileId;
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
    public static class FilmDtoBuilder {

        private int id;
        private String name;
        private String description;
        private int year;
        private int minimalAge;
        private int durationInMinutes;
        private String genre;
        private int fileId;

        public FilmDtoBuilder() {
            super();
        }

        public FilmDtoBuilder id(int id) {
            this.id = id;
            return this;
        }

        public FilmDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public FilmDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public FilmDtoBuilder year(int year) {
            this.year = year;
            return this;
        }

        public FilmDtoBuilder minimalAge(int minimalAge) {
            this.minimalAge = minimalAge;
            return this;
        }

        public FilmDtoBuilder durationInMinutes(int durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public FilmDtoBuilder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public FilmDtoBuilder fileId(int fileId) {
            this.fileId = fileId;
            return this;
        }

        public FilmDto build() {
            return new FilmDto(this);
        }
    }
}
