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

    /**
     * Sql2o - небольшая и быстрая библиотека
     * для доступа к реляционным базам данных.
     * По сути это клиент для работы с БД.
     */
    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Данный метод сохраняет билет
     * в базу данных.
     *
     * Т.к. билет должен быть уникальным,
     * мы ловим исключение и вручную
     * обрабатываем, чтобы на проде
     * не получить ошибку. Таким
     * образом, если билет будет уже
     * куплен кем-то, то мы вернем
     * пустой Optional.
     *
     * Напоминание (в учебных целях):
     * - метод ecexute() возвращает boolean;
     * - метод executeUpdate() возвращает
     * int, которое сообщает, сколько строк
     * было изменено;
     * - метод executeQuery() возвращает ResultSet;
     */
    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var sql = """
                    insert into tickets(session_id, row_number, place_number, user_id)
                    values (:sessionId, :rowNumber, :placeNumber, :userId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.sessionId())
                    .addParameter("rowNumber", ticket.rowNumber())
                    .addParameter("placeNumber", ticket.placeNumber())
                    .addParameter("userId", ticket.userId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Sql2oException e) {
            LOG.error("SQL EXCEPTION LOGGED: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Данный метод ищет билет по номеру
     * места и ряда.
     *
     * В методе
     * {@link org.sql2o.Query#executeAndFetchFirst}
     * мы указываем, какой тип хотим возвращать.
     * Здесь мы указали, что нам требуется вернуть
     * объект класса {@link Ticket} (первый из списка,
     * потому что метод
     * {@link org.sql2o.Query#executeAndFetch}
     * возвращает список).
     */
    @Override
    public Optional<Ticket> findByRowAndSeatNoAndSessionId(int rowNo, int seatNo, int sessionId) {
        try (var connection = sql2o.open()) {
            var sql = """
                    select * from tickets
                    where row_number = :rowNo and place_number = :seatNo and session_id = :sessionId
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("rowNo", rowNo)
                    .addParameter("seatNo", seatNo)
                    .addParameter("sessionId", sessionId);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }
}
