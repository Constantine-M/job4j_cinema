package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.film.FilmService;
import ru.job4j.cinema.service.session.FilmSessionService;
import ru.job4j.cinema.service.hall.HallService;

/**
 * Данный класс находится в слое
 * контроллеров и обрабатывает запросы
 * пользователя.
 *
 * Здесь будут сгруппированы запросы,
 * касающиеся кинотеки и сеансов с
 * фильмами.
 *
 * @author Constantine on 22.11.2023
 */
@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final FilmSessionService filmSessionService;
    private final HallService hallService;

    public FilmController(FilmService filmService,
                          FilmSessionService filmSessionService,
                          HallService hallService) {
        this.filmService = filmService;
        this.filmSessionService = filmSessionService;
        this.hallService = hallService;
    }

    /**
     * Данный метод обрабатывает запрос
     * на отображение всех фильмов,
     * которые показываются в кинотеатре.
     *
     * Метод принимает объект Model.
     * Он используется Thymeleaf для поиска
     * объектов, которые нужны отобразить на виде.
     *
     * В Model мы добавляем объект films.
     *
     * @param model модель, которую необходимо
     *              отобразить на виде.
     */
    @GetMapping
    public String getAllFilms(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "films/list";
    }

    @GetMapping("/schedule")
    public String getAllSessions(Model model) {
        model.addAttribute("sessions", filmSessionService.findAll());
        return "films/schedule";
    }

    /**
     * Данный метод обрабатывает запрос на
     * выбор конкретного сеанса и переводит
     * пользователя на страницу покупки.
     *
     * Ранее, чтобы один контроллер мог передать
     * эстафету другому контроллеру, мы на HTML
     * странице schedule.html настроили ссылку
     * на /tickets/{id}, и, таким образом,
     * дальнейшие действия по обработке запросов
     * выполняет уже {@link TicketController},
     * потому что на этом этапе мы работаем
     * только с билетами.
     *
     * Чтобы снова не искать пользователя, я решил
     * его взять из сессии. Прежде чем он дойдет
     * до страницы покупки, user пройдет
     * аутентификацию и будет  авторизован
     * на сайте. Для этого я передал в метод
     * {@link HttpSession}.
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable int id,
                          Model model,
                          HttpSession session) {
        var user = (User) session.getAttribute("user");
        var filmSession = filmSessionService.findById(id);
        model.addAttribute("filmSession", filmSession);
        model.addAttribute("user", user);
        model.addAttribute("hall", hallService.findById(filmSession.hallId()));
        return "tickets/order";
    }
}
