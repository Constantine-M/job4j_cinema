package ru.job4j.cinema.service.session;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.session.FilmSessionRepository;
import ru.job4j.cinema.repository.hall.HallRepository;

import java.util.Collection;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;

    private final FilmRepository filmRepository;

    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository,
                                    FilmRepository filmRepository,
                                    HallRepository hallRepository) {

        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
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
                        .hallId(filmSession.hallId())
                        .hallName(hallRepository.findById(filmSession.hallId()).name())
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
                .hallId(filmSession.hallId())
                .hallName(hallRepository.findById(filmSession.hallId()).name())
                .build();
    }
}
