package ru.job4j.cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;

/**
 * Данный интерфейс описывает
 * преобразование сущности (Entity)
 * {@link Film} в DTO {@link FilmDto}.
 *
 * Для преобразования была выбрана
 * библиотека MapStruct.
 *
 * В аннотации {@link Mapper} свойство
 * componentModel = "spring" позволяет
 * добавить наш маппер в контекст.
 */
@Mapper(componentModel = "spring")
public interface FilmMapper {

    @Mapping(source = "film.id", target = "id")
    @Mapping(source = "film.name", target = "name")
    @Mapping(source = "genre.name", target = "genre")
    @Mapping(source = "film.year", target = "year")
    @Mapping(source = "film.description", target = "description")
    @Mapping(source = "film.minimalAge", target = "minimalAge")
    @Mapping(source = "film.durationInMinutes", target = "durationInMinutes")
    @Mapping(source = "film.fileId", target = "fileId")
    FilmDto getFilmDto(Film film, Genre genre);
}
