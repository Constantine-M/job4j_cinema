package ru.job4j.cinema.service.session;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.mapper.FilmMapper;
import ru.job4j.cinema.mapper.FilmSessionMapper;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.session.FilmSessionRepository;
import ru.job4j.cinema.repository.hall.HallRepository;

import java.util.Collection;

/**
 * В данном классе для преобразования
 * сущности в DTO используем библиотеку
 * MapStruct.
 *
 * Для каждого объекта DTO был
 * написан свой маппер.
 * Здесь это {@link FilmSessionMapper}.
 */
@AllArgsConstructor
@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;

    private final FilmRepository filmRepository;

    private final HallRepository hallRepository;

    private final FilmSessionMapper filmSessionMapper;

    @Override
    public Collection<FilmSessionDto> findAll() {
        var filmSessionList = filmSessionRepository.findAll();
        var filmSessionDtoList = filmSessionList.stream()
                .map(filmSession -> filmSessionMapper.getFilmSessionDto(
                        filmSession,
                        filmRepository.findById(filmSession.getFilmId()),
                        hallRepository.findById(filmSession.getHallId()))
                )
                .toList();
        return filmSessionDtoList;
    }

    @Override
    public FilmSessionDto findById(int id) {
        var filmSession = filmSessionRepository.findById(id);
        var film = filmRepository.findById(filmSession.getFilmId());
        var hall = hallRepository.findById(filmSession.getHallId());
        return filmSessionMapper.getFilmSessionDto(filmSession, film, hall);
    }
}
