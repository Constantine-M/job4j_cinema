package ru.job4j.cinema.service.ticket;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.session.FilmSessionRepository;
import ru.job4j.cinema.repository.ticket.TicketRepository;

import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    private final FilmSessionRepository filmSessionRepository;

    private final FilmRepository filmRepository;

    public SimpleTicketService(TicketRepository ticketRepository,
                               FilmSessionRepository filmSessionRepository,
                               FilmRepository filmRepository) {

        this.ticketRepository = ticketRepository;
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public Optional<TicketDto> buyTicket(Ticket ticket) {
        if (isTaken(ticket)) {
            return Optional.empty();
        }
        var savedTicketOptional = ticketRepository.save(ticket);
        var filmSession = filmSessionRepository.findById(savedTicketOptional.get().sessionId());
        var ticketDto = new TicketDto.TicketDtoBuilder()
                            .filmName(filmRepository.findById(filmSession.filmId()).name())
                            .startTime(filmSession.startTime())
                            .endTime(filmSession.endTime())
                            .hallId(filmSession.hallId())
                            .price(filmSession.price())
                            .rowNumber(savedTicketOptional.get().rowNumber())
                            .seatNumber(savedTicketOptional.get().placeNumber())
                            .build();
        return Optional.of(ticketDto);
    }

    /**
     * Данный метод проверяет наличие
     * билета по ряду, месту и номеру сеанса.
     *
     * @return true, если билет уже
     * куплен/место занято.
     */
    private boolean isTaken(Ticket ticket) {
        return ticketRepository
                .findByRowAndSeatNoAndSessionId(
                        ticket.rowNumber(), ticket.placeNumber(), ticket.sessionId())
                .isPresent();
    }
}
