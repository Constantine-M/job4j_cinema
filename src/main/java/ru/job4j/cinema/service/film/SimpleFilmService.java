package ru.job4j.cinema.service.film;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.mapper.FilmMapper;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.genre.GenreRepository;

import java.util.Collection;

/**
 * В данном классе для преобразования
 * сущности в DTO используем библиотеку
 * MapStruct.
 *
 * Для каждого объекта DTO был
 * написан свой маппер.
 * Здесь это {@link FilmMapper}.
 */
@Service
@AllArgsConstructor
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final GenreRepository genreRepository;

    private final FilmMapper filmMapper;

    @Override
    public Collection<FilmDto> findAll() {
        var films = filmRepository.findAll();
        var filmDtoResultList = films.stream()
                .map(film -> filmMapper.getFilmDto(
                        film,
                        genreRepository.findById(film.getId()))
                )
                .toList();
        return filmDtoResultList;
    }

    @Override
    public FilmDto findById(int id) {
        var film = filmRepository.findById(id);
        var genre = genreRepository.findById(id);
        return filmMapper.getFilmDto(film, genre);
    }
}
