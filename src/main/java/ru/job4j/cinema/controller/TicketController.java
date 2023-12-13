package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.ticket.TicketService;

/**
 * @author Constantine on 28.11.2023
 */
@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Данный метод обрабатывает запрос
     * на покупку билета.
     *
     * 1.Используя аннотацию
     * {@link ModelAttribute} сообщаем Spring,
     * чтобы тот собрал объект {@link Ticket}
     * из параметров запроса. При этом
     * следует НЕ ЗАБЫВАТЬ создавать пустой
     * конструктор в классе модели и
     * проинициализировать поля, которые не
     * участвуют при маппинге.
     *
     * Для страниц с запросом POST не нужно
     * писать метод с GetMapping.
     * То есть я в {@link FilmController}
     * сразу перебросил пользователя на
     * страницу tickets/order, где выполнится
     * POST запрос.
     *
     * После этого, в случае успеха, переводим
     * пользователя на страницу успешной
     * покупки билета. Для страницы success.html
     * тоже не нужно писать GetMapping.
     * В этом же методе мы передали модель в
     * шаблонизатор для отображения всей нужной
     * информации на странице.
     */
    @PostMapping("/order")
    public String saveTicket(@ModelAttribute Ticket ticket,
                             Model model) {
        if (rowNoOrSeatNoIsEqualToZero(ticket)) {
            model.addAttribute("error", "Maybe you distracted, and forget to choose the row number or seat number. Please, try again.");
            return "errors/404";
        }
        var ticketOptional = ticketService.buyTicket(ticket);
        if (ticketOptional.isEmpty()) {
            model.addAttribute("error", "Could not purchase a ticket for the specified place. Probably it is already occupied. Go to the booking page and try again.");
            return "errors/404";
        }
        model.addAttribute("ticket", ticketOptional.get());
        return "tickets/success";
    }

    /**
     * Данный метод проверяет, что пользователь
     * выбрал ряд, отличный от нулевого.
     *
     * Т.к. не удается произвести валидацию на стороне
     * пользователя, не удается произвести перезагрузку
     * страницы, было принято такое решение.
     *
     * Если в форме ничего не выбирать, то
     * по умолчанию выберется 0. Также я знаю, что
     * можно настроить значения по умолчанию,
     * но форма будет некрасивой.
     */
    private boolean rowNoOrSeatNoIsEqualToZero(Ticket ticket) {
        return ticket.rowNumber() == 0 || ticket.placeNumber() == 0;
    }
}
