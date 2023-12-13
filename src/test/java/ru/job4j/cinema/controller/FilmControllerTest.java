package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.film.FilmService;
import ru.job4j.cinema.service.session.FilmSessionService;
import ru.job4j.cinema.service.hall.HallService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmControllerTest {

    private FilmService filmService;

    private FilmSessionService filmSessionService;

    private HallService hallService;

    private FilmController filmController;

    @BeforeEach
    public void initServices() {
        filmService = mock(FilmService.class);
        filmSessionService = mock(FilmSessionService.class);
        hallService = mock(HallService.class);
        filmController = new FilmController(filmService, filmSessionService, hallService);
    }

    /**
     * Тест проверяет ситуацию, когда
     * запрашивается страница со всеми фильмами,
     * которые крутят в кино.
     */
    @Test
    public void whenRequestListOfFilmsThenGetPageWithFilms() {
        var film1 = new FilmDto.FilmDtoBuilder()
                .id(1)
                .name("Big Bang")
                .genre("action")
                .year(1)
                .description("About big bang")
                .minimalAge(3)
                .durationInMinutes(1)
                .fileId(1)
                .build();
        var film2 = new FilmDto.FilmDtoBuilder()
                .id(2)
                .name("Big Bang. Part 2")
                .genre("fantasy")
                .year(1)
                .description("About big bang that won’t happen")
                .minimalAge(11)
                .durationInMinutes(90)
                .fileId(1)
                .build();
        var expectedFilms = List.of(film1, film2);
        when(filmService.findAll()).thenReturn(expectedFilms);

        var model = new ConcurrentModel();
        var view = filmController.getAllFilms(model);
        var actualFilmList = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualFilmList).isEqualTo(expectedFilms);
    }

    /**
     * Тест проверяет ситуацию, когда
     * запрашивается страница со всеми
     * киносеансами.
     */
    @Test
    public void whenRequestScheduleOfFilmsThenGetSchedulePage() {
        var dateTime = LocalDateTime.now();
        var filmSession1 = new FilmSessionDto.FilmSessionDtoBuilder()
                .id(1)
                .filmName("Big Bang")
                .startTime(dateTime)
                .endTime(dateTime.plusMinutes(2))
                .price(100000)
                .hallId(1)
                .hallName("Earth")
                .build();
        var filmSession2 = new FilmSessionDto.FilmSessionDtoBuilder()
                .id(1)
                .filmName("Big Bang. Part 2")
                .startTime(dateTime)
                .endTime(dateTime.plusMinutes(180))
                .price(10000000)
                .hallId(1)
                .hallName("Earth???")
                .build();
        var expectedSessionList = List.of(filmSession1, filmSession2);
        when(filmSessionService.findAll()).thenReturn(expectedSessionList);

        var model = new ConcurrentModel();
        var view = filmController.getAllSessions(model);
        var actualSessionList = model.getAttribute("sessions");

        assertThat(view).isEqualTo("films/schedule");
        assertThat(actualSessionList).isEqualTo(expectedSessionList);
    }

    /**
     * Тест проверяет ситуацию, когда
     * запрашивается страница по конкретному
     * ID киносеанса. В результате получаем
     * страницу покупки билета, на которой
     * будут отображены данные киносенса
     * и данные по залу (номер зала, номер ряда,
     * номер места).
     *
     * Варианта, когда такой сеанс не находится,
     * нет и он не тестируется, потому что
     * для пользователя выводится список всех
     * доступных сеансов из БД. Каждому сеансу
     * соответствует свой ID. По ТЗ пользователь
     * сам не осуществляет поиск сеанса по каким-либо
     * параметрам.
     */
    @Test
    public void whenRequestFilmSessionByIdThenGetOrderPageWithFilmSessionAndHallAttr() {
        var dateTime = LocalDateTime.now();
        var session = new MockHttpSession();
        var expectedFilmSession = new FilmSessionDto.FilmSessionDtoBuilder()
                .id(1)
                .filmName("Big Bang")
                .startTime(dateTime)
                .endTime(dateTime.plusMinutes(2))
                .price(100000)
                .hallId(1)
                .hallName("Earth")
                .build();
        var expectedHall = new Hall(1, "Earth", 10, 100, "The biggest hall in the world");
        when(filmSessionService.findById(anyInt())).thenReturn(expectedFilmSession);
        when(hallService.findById(anyInt())).thenReturn(expectedHall);

        var model = new ConcurrentModel();
        var view = filmController.getById(1, model, session);
        var actualFilmSession = model.getAttribute("filmSession");
        var actualHall = model.getAttribute("hall");

        assertThat(view).isEqualTo("tickets/order");
        assertThat(actualFilmSession).usingRecursiveComparison().isEqualTo(expectedFilmSession);
        assertThat(actualHall).usingRecursiveComparison().isEqualTo(expectedHall);
    }
}