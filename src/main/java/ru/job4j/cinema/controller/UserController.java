package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.user.UserService;

/**
 * Данный класс находится в слое
 * контроллеров и обрабатывает запросы
 * пользователя.
 *
 * Работать с пользователями будем по URI /users
 * URI – имя и адрес ресурса в сети, включает
 * в себя URL и URN;
 * URL – адрес ресурса в сети, определяет
 * местонахождение и способ обращения к нему;
 * URN – имя ресурса в сети, определяет только
 * название ресурса, но не говорит как к
 * нему подключиться;
 *
 * @author Constantine on 20.11.2023
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Данный метод обрабатывает запрос,
     * согласно которому мы получаем
     * непосредственно сверстанную
     * html страничку.
     */
    @GetMapping("/register")
    public String getRegistrationPage() {
        return "users/register";
    }

    /**
     * Данный метод обрабатывает запрос
     * на регистрацию пользователя.
     *
     * После успешной регистрации
     * пользователя перебрасывает на
     * страницу с сеансами.
     */
    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                           Model model) {
        var savedUser = userService.save(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "User already exists with this email!");
            return "users/register";
        }
        return "redirect:/films";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    /**
     * Данный метод обрабатывает запрос на
     * авторизацию пользователя.
     *
     * Чтобы закрепить открытую сессию
     * за пользователем, воспользуемся
     * интерфейсом {@link HttpServletRequest}
     * и получим сессию.
     *
     * Метод {@link HttpServletRequest#getSession()}
     * вернет нам объект {@link HttpSession}.
     *
     * А затем добавим данные в сессию
     * с помощью метода {@link HttpSession#setAttribute}.
     * Например, добавим туда пользователя.
     * Т.о. мы закрепили пользователя за сессией.
     * Данные сессии привязываются к клиенту
     * и доступны во всем приложении.
     *
     * Обрати внимание на то, что внутри HttpSession
     * используется многопоточная коллекция
     * ConcurrentHashMap. Это связано с многопоточным
     * окружением. Увидеть это можно в реализации
     * модуля catalina -> session
     * {@link org.apache.catalina.session.StandardSession}.
     */
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user,
                            Model model,
                            HttpSession session) {
        var userOptional = userService.findByEmailAndPassword(user.email(), user.password());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "The email or password is incorrect");
            return "users/login";
        }
        session.setAttribute("user", userOptional.get());
        return "redirect:/films";
    }

    /**
     * Данный метод обрабатывает запрос
     * пользователя, который закрывает сессию
     * (разлогинивается).
     *
     * В данном случае, чтобы удалить
     * все данные, связанные с пользователем,
     * нужно использовать метод
     * {@link HttpSession#invalidate()}.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
