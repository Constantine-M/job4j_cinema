package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.session.Sql2oFilmSessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oFilmSessionRepositoryTest {

    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    /**
     * Находим все киносенасы.
     *
     * Т.к. мы заранее заполнили БД, то
     * всего киносеансов - 3. Т.к. мы не
     * создавали их, то время пришлось
     * прописывать таким длинным способом.
     */
    @Test
    void whenFindAllFilmSessionsThenGetListOfThreeFilmSession() {
        var dateTime = LocalDateTime.of(2023, 11, 4, 22, 0);
        var expectedListOfSessions = List.of(
                new FilmSession(1, 1, 3, dateTime, dateTime.plusMinutes(30), 1200),
                new FilmSession(2, 2, 1, dateTime.minusHours(1), dateTime.plusMinutes(30), 1650),
                new FilmSession(3, 3, 2, dateTime.minusHours(3), dateTime.plusMinutes(3), 150)
        );
        var actualListOfSessions = sql2oFilmSessionRepository.findAll();
        assertThat(actualListOfSessions).isEqualTo(expectedListOfSessions);
    }

    /**
     * По данному сценарию мы находим
     * киносенас по его ID.
     */
    @Test
    void whenFindById3ThenGetFilmSessionWithStartTimeAt9AM() {
        var dateTime = LocalDateTime.of(2023, 11, 5, 9, 30);
        var expectedFilmSession = new FilmSession(3, 3, 2, dateTime, dateTime.plusHours(2), 150);
        assertThat(sql2oFilmSessionRepository.findById(3)).isEqualTo(expectedFilmSession);
    }
}