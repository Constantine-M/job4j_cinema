package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<Ticket> buyTicket(Ticket ticket) {
        if (isTaken(ticket)) {
            return Optional.empty();
        }
        return ticketRepository.save(ticket);
    }

    /**
     * Данный метод проверяет наличие
     * билета по ряду и месту.
     *
     * @return true, если билет уже
     * куплен/место занято.
     */
    private boolean isTaken(Ticket ticket) {
        var optionalTicket = ticketRepository.findByRowAndSeatNo(ticket.rowNumber(), ticket.placeNumber());
        return optionalTicket.isPresent();
    }
}
