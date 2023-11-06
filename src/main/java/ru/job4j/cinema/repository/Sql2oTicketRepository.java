package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oTicketRepository.class);

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var sql = """
                    insert into tickets(session_id, row_number, place_number, user_id)
                    values (:sessionId, :rowNumber, :placeNumber, userId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("session_id", ticket.sessionId())
                    .addParameter("row_number", ticket.rowNumber())
                    .addParameter("place_number", ticket.placeNumber())
                    .addParameter("user_id", ticket.userId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Sql2oException e) {
            LOG.error("SQL EXCEPTION LOGGED: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
