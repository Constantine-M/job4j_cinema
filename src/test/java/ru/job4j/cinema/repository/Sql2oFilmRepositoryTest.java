package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;


    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    /**
     * В тестовой БД 3 фильма. Задачи добавлять
     * фильмы, как отдельную функцию, не было.
     * Поэтому 3 фильма в БД заранее добавлены.
     *
     * Когда запрашиваем все фильмы - получаем
     * список из 3 объектов {@link Film}.
     */
    @Test
    void whenFindAllThenGetListOfThreeFilms() {
        var expectedList = List.of(
                new Film(1, "Scary Movie", "You Think Its a Joke?", 2000, 1, 12, 88, 1),
                new Film(2, "Zombieland", "Our land is their land.", 2009, 1, 18, 84, 2),
                new Film(3, "The Addams Family", "Weird is relative.", 1991, 2, 12, 102, 3)
        );
        var actualList = sql2oFilmRepository.findAll();
        assertThat(actualList).isEqualTo(expectedList);
    }

    /**
     * Т.к. фильмы были добавлены в БД заранее,
     * то по сценарию был осуществлен поиск фильма
     * по ID = 2.
     *
     * Согласно ТЗ, пользователь не может осуществлять
     * поиск фильмов, поэтому не использовался
     * {@link java.util.Optional}. То есть фильм
     * всегда должен быть найден.
     */
    @Test
    void whenFindById2ThenGetFilmZombieland() {
        var expectedFilm = new Film(2, "Zombieland", "Our land is their land.", 2009, 1, 18, 84, 2);
        assertThat(sql2oFilmRepository.findById(2)).isEqualTo(expectedFilm);
    }
}