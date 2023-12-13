package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.user.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    /**
     * Напомню, что метод
     * usingRecursiveComparison()
     * сравнивает данные объектов.
     *
     * Метод isEqualTo() сравнивает ссылки.
     */
    @Test
    public void whenRegisterSuccessfullyThenRedirectToFilmsPage() {
        var user = new User(1, "Consta", "test@mail.ru", "qwerty");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(user, model);
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/films");
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenRequestRegisterUserWithExistedMailThenGetBackToRegisterPage() {
        var expectedErrorMessage = "User already exists with this email!";
        var user = new User(1, "Consta", "test@mail.ru", "qwerty");
        when(userService.save(user)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(user, model);
        var actualErrorMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("users/register");
        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    /**
     * В данном тесте используется класс {@link ArgumentCaptor}.
     * Этот класс позволяет "захватить" аргумент, который
     * передается в метод. Он удобен при тестировании
     * методов, аргументы которых вычисляются, как,
     * например, при создании FileDto.
     *
     * Мы не можем получить их как возвращаемые значения!
     *
     * В месте вызова метода нам нужно вызывать метод
     * {@link ArgumentCaptor#capture()}.
     * Для получения переловленного значения нужно
     * вызвать метод {@link ArgumentCaptor#getValue()}.
     *
     * Вариант замокать {@link HttpSession} - это
     * использовать {@link MockHttpSession}.
     */
    @Test
    public void whenRequestLoginUserSuccessfullyThenRedirectToFilmsPage() {
        var user = new User(1, "Consta", "test@mail.ru", "qwerty");
        var session = new MockHttpSession();
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, session);
        session.setAttribute("user", user);

        assertThat(view).isEqualTo("redirect:/films");
    }

    @Test
    public void whenRequestLoginUserWithWrongLogopassThenGetBackToLoginPage() {
        var expectedErrorMessage = "The email or password is incorrect";
        var user = new User(1, "Consta", "test@mail.ru", "qwerty");
        var session = new MockHttpSession();
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, session);
        var actualErrorMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    @Test
    public void whenRequestLogoutThenRedirectToLoginPage() {
        var user = new User(1, "Consta", "test@mail.ru", "qwerty");
        var session = new MockHttpSession();
        session.setAttribute("user", user);

        var view = userController.logout(session);

        assertThat(view).isEqualTo("redirect:/users/login");
    }

}