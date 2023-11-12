package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;

import java.util.Collection;

public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;

    private final FilmRepository filmRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository, FilmRepository filmRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        var filmSessionList = filmSessionRepository.findAll();
        var filmSessionDtoList = filmSessionList.stream()
                .map(filmSession -> new FilmSessionDto.FilmSessionDtoBuilder()
                        .id(filmSession.id())
                        .filmName(filmRepository.findById(filmSession.filmId()).name())
                        .startTime(filmSession.startTime())
                        .endTime(filmSession.endTime())
                        .price(filmSession.price())
                        .build())
                .toList();
        return filmSessionDtoList;
    }

    @Override
    public FilmSessionDto findById(int id) {
        var filmSession = filmSessionRepository.findById(id);
        var film = filmRepository.findById(filmSession.filmId());
        return new FilmSessionDto.FilmSessionDtoBuilder()
                .id(filmSession.id())
                .filmName(film.name())
                .startTime(filmSession.startTime())
                .endTime(filmSession.endTime())
                .price(filmSession.price())
                .build();
    }
}
