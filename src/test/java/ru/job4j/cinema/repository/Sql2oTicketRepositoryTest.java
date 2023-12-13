package ru.job4j.cinema.repository;

import org.junit.jupiter.api.*;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.ticket.Sql2oTicketRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oTicketRepositoryTest {

    private static Sql2oTicketRepository sql2oTicketRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    public void clearTickets() {
        sql2oTicketRepository.findAll()
                .forEach(ticket -> sql2oTicketRepository.deleteById(ticket.id()));
    }

    /**
     * Тест проверяет случай, когда пользователь
     * успешно приобретает билет (покупает и
     * получает его).
     */
    @Test
    void whenSaveTicketThenGetTheSame() {
        var ticket = new Ticket(0, 1, 1, 1, 1);
        var savedTicket = sql2oTicketRepository.save(ticket).get();
        var expectedTicket = sql2oTicketRepository.findByRowAndSeatNoAndSessionId(ticket.rowNumber(), ticket.placeNumber(), ticket.sessionId()).get();
        assertThat(expectedTicket).usingRecursiveComparison().isEqualTo(savedTicket);
    }

    /**
     * Тест проверяет случай, когда покупка
     * билета не совершалась. Список билетов
     * у пользователя должен быть пустой.
     */
    @Test
    void whenDontSaveThenNothingFound() {
        assertThat(sql2oTicketRepository.findAll()).isEqualTo(Collections.emptyList());
    }

    /**
     * Тест проверяет случай, когда пользователь
     * пытается купить билет на сеанс и выбирает
     * уже занятые ряд и место. В этом случае
     * он ничего не получает (билет купить невозможно).
     */
    @Test
    void whenSaveTicketsWithTheSameSeatAndRowAndSessionThenGetNothing() {
        var ticket = new Ticket(0, 1, 1, 1, 1);
        var savedTicket = sql2oTicketRepository.save(ticket).get();
        assertThat(sql2oTicketRepository.save(savedTicket)).isEqualTo(Optional.empty());
    }
}