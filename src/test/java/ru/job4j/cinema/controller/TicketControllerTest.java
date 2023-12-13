package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.ticket.TicketService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    private TicketService ticketService;

    private TicketController ticketController;

    @BeforeEach
    public void initServices() {
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(ticketService);
    }

    /**
     * Тест проверяет ситуацию, когда
     * пользовыатель совершает успешную
     * покупку билета. После успешной покупки
     * пользователя перебрасывает на страницу
     * успешного заказа с информацией о зале/
     * сеансе/времени и т.д., которые он выбрал.
     *
     * Была трудность, какой объект билета
     * захватывать. В итоге принял решение
     * захватывать именно {@link Ticket},
     * т.к. именно его мы передаем в метод
     * (именно {@link Ticket} хочет купить
     * пользователь, а не {@link TicketDto}.
     *
     В данном тесте используется класс {@link ArgumentCaptor}.
     * Этот класс позволяет "захватить" аргумент,
     * который передается в метод. Он удобен
     * при тестировании методов, аргументы которых
     * вычисляются, как, апример, при создании
     * FileDto.
     *
     * Мы не можем получить их как возвращаемые значения!
     *
     * В месте вызова метода нам нужно вызывать
     * метод @link ArgumentCaptor#capture()}.
     * Для получения переловленного значения
     * нужно вызвать метод {@link ArgumentCaptor#getValue()}.
     */
    @Test
    public void whenBuyTicketSuccessfullyThenGetSameDataAndRedirectToSuccessOrderPage() {
        var dateTime = LocalDateTime.now();
        var ticket = new Ticket(1, 1, 7, 12, 1);
        var ticketDto = new TicketDto.TicketDtoBuilder()
                .filmName("The Big Bang Theory")
                .startTime(dateTime)
                .endTime(dateTime.plusMinutes(3))
                .hallId(1)
                .price(10000)
                .rowNumber(7)
                .seatNumber(12)
                .build();
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        when(ticketService.buyTicket(ticketArgumentCaptor.capture())).thenReturn(Optional.of(ticketDto));

        var model = new ConcurrentModel();
        var view = ticketController.saveTicket(ticket, model);
        var actualTicket = ticketArgumentCaptor.getValue();

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    /**
     * Тест проверяет ситуацию, когда
     * пользователь не указал ряд или место.
     * В этом случае юзера перенаправляет
     * на страницу ошибки.
     *
     * В данном проекте мне не удалось
     * произвести проверку формы на стороне
     * пользователя. Я хотел сделать форму
     * максимально мнималистичной, поэтому
     * вышло так что пользователь, забыв
     * указать место или ряд, отправляет в форму
     * нули (0). Купить билет ему не удастся, но
     * тесты пришлось написать под этот случай.
     */
    @Test
    public void whenBuyTicketWithZeroRowNoThenGetErrorPageWithMessage() {
        var dateTime = LocalDateTime.now();
        var ticket = new Ticket(1, 1, 0, 12, 1);
        var ticketDto = new TicketDto.TicketDtoBuilder()
                .filmName("The Big Bang Theory")
                .startTime(dateTime)
                .endTime(dateTime.plusMinutes(3))
                .hallId(1)
                .price(10000)
                .rowNumber(0)
                .seatNumber(12)
                .build();
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        var expectedErrorMessage = "Maybe you distracted, and forget to choose the row number or seat number. Please, try again.";
        when(ticketService.buyTicket(ticketArgumentCaptor.capture())).thenReturn(Optional.of(ticketDto));

        var model = new ConcurrentModel();
        var view = ticketController.saveTicket(ticket, model);
        var actualErrorMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("errors/404");
        assertThat(expectedErrorMessage).isEqualTo(actualErrorMessage);
    }

    @Test
    public void whenBuyTicketWithZeroSeatNoThenGetErrorPageWithMessage() {
        var dateTime = LocalDateTime.now();
        var ticket = new Ticket(1, 1, 4, 0, 1);
        var ticketDto = new TicketDto.TicketDtoBuilder()
                .filmName("The Big Bang Theory")
                .startTime(dateTime)
                .endTime(dateTime.plusMinutes(3))
                .hallId(1)
                .price(10000)
                .rowNumber(7)
                .seatNumber(0)
                .build();
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        var expectedErrorMessage = "Maybe you distracted, and forget to choose the row number or seat number. Please, try again.";
        when(ticketService.buyTicket(ticketArgumentCaptor.capture())).thenReturn(Optional.of(ticketDto));

        var model = new ConcurrentModel();
        var view = ticketController.saveTicket(ticket, model);
        var actualErrorMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("errors/404");
        assertThat(expectedErrorMessage).isEqualTo(actualErrorMessage);
    }

    /**
     * Тест проверяет ситуацию, когда
     * пользователь покупает билет на уже
     * занятые ряд и место.
     *
     * В этом случае его перенаправляет
     * на страницу ошибки.
     */
    @Test
    public void whenBuyTicketWithTheSameRowNoAndSeatNoAndSessionNoThenGetErrorPageWithMessage() {
        var ticket = new Ticket(1, 1, 4, 5, 1);
        var expectedErrorMessage = "Could not purchase a ticket for the specified place. Probably it is already occupied. Go to the booking page and try again.";
        when(ticketService.buyTicket(any())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = ticketController.saveTicket(ticket, model);
        var actualErrorMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }
}