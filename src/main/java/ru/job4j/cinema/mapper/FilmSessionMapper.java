package ru.job4j.cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;

/**
 * Данный интерфейс описывает
 * преобразование сущности (Entity)
 * {@link FilmSession} в DTO {@link FilmSessionDto}.
 * Помимо {@link FilmSession} в
 * преобразовании будут участвовать
 * сущности {@link Film} и {@link Hall}.
 *
 * Для преобразования была выбрана
 * библиотека MapStruct.
 *
 * В аннотации {@link Mapper} свойство
 * componentModel = "spring" позволяет
 * добавить наш маппер в контекст.
 */
@Mapper(componentModel = "spring")
public interface FilmSessionMapper {

    @Mapping(source = "filmSession.id", target = "id")
    @Mapping(source = "film.name", target = "filmName")
    @Mapping(source = "filmSession.startTime", target = "startTime")
    @Mapping(source = "filmSession.endTime", target = "endTime")
    @Mapping(source = "filmSession.price", target = "price")
    @Mapping(source = "hall.id", target = "hallId")
    @Mapping(source = "hall.name", target = "hallName")
    FilmSessionDto getFilmSessionDto(FilmSession filmSession,
                                     Film film,
                                     Hall hall);
}
