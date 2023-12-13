package ru.job4j.cinema.service.film;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.genre.GenreRepository;

import java.util.Collection;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository filmRepository, GenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Collection<FilmDto> findAll() {
        var films = filmRepository.findAll();
        var filmDtoResultList = films.stream()
                .map(film -> new FilmDto.FilmDtoBuilder()
                        .id(film.id())
                        .name(film.name())
                        .genre(genreRepository.findById(film.genreId()).name())
                        .year(film.year())
                        .description(film.description())
                        .minimalAge(film.minimalAge())
                        .durationInMinutes(film.durationInMinutes())
                        .fileId(film.fileId())
                        .build())
                .toList();
        return filmDtoResultList;
    }

    @Override
    public FilmDto findById(int id) {
        var film = filmRepository.findById(id);
        var genre = genreRepository.findById(id);
        return new FilmDto.FilmDtoBuilder()
                .id(film.id())
                .name(film.name())
                .genre(genre.name())
                .year(film.year())
                .description(film.description())
                .durationInMinutes(film.durationInMinutes())
                .minimalAge(film.minimalAge())
                .build();
    }
}
