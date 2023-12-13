package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.user.Sql2oUserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        sql2oUserRepository.findAll()
                .forEach(user -> sql2oUserRepository.deleteById(user.id()));
    }

    /**
     * Данный тест проверяет случай, когда
     * пользователь успешно регистрируется
     * на сайте (успешно добавляется в БД).
     */
    @Test
    void whenSaveUserThenGetSame() {
        var user = new User(0, "Constantine", "test@mail.ru", "123");
        var savedUser = sql2oUserRepository.save(user);
        var expectedUser = sql2oUserRepository.findByEmailAndPassword(user.email(), user.password());
        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(savedUser);
    }

    /**
     * Данный тест проверяет случай, когда
     * пользователь пытается зарегистрироваться
     * на сайте с email, который уже занят другим
     * пользователем. В этом случае зарегистрироваться
     * невозможно.
     */
    @Test
    void whenSaveUserWithTheSameEmailThenGetNothing() {
        var user = new User(0, "Constantine", "test@mail.ru", "123");
        sql2oUserRepository.save(user);
        assertThat(sql2oUserRepository.save(user)).isEqualTo(Optional.empty());
    }

    /**
     * Данный тест проверяет случай, когда
     * несколько пользователей успешно
     * регистрируются на сайте (успешно добавляются
     * в базу данных пользователей).
     */
    @Test
    void whenSaveSeveralUsersThenGetAll() {
        var user1 = sql2oUserRepository.save(new User(0, "Constantine", "test@mail.ru", "qwerty")).get();
        var user2 = sql2oUserRepository.save(new User(0, "Constantine", "est@mail.ru", "qwert")).get();
        var user3 = sql2oUserRepository.save(new User(0, "Constantine", "st@mail.ru", "qwer")).get();
        var userList = sql2oUserRepository.findAll();
        assertThat(userList).isEqualTo(List.of(user1, user2, user3));
    }

    /**
     * Данный тест проверяет случай, когда
     * пользователь не был добавлен в БД.
     * В этом случае список должен быть пустой.
     */
    @Test
    void whenDontSaveThenNothingFound() {
        assertThat(sql2oUserRepository.findAll()).isEqualTo(Collections.emptyList());
    }

    /**
     * Данный тест проверяет случай, когда
     * пользовател успешно удаляется из БД.
     * Если он был единственный, то после удаления
     * список должен быть пустой, а поиск
     * пользователя по имени почтового ящика
     * ничего не возвращает (не найден).
     */
    @Test
    void whenDeleteThenGetEmptyOptional() {
        var user = sql2oUserRepository.save(new User(0, "Constantine", "test@mail.ru", "qwerty")).get();
        var isDeletedUser = sql2oUserRepository.deleteById(user.id());
        var savedUser = sql2oUserRepository.findByEmailAndPassword("test@mail.ru", "qwerty");
        assertThat(isDeletedUser).isTrue();
        assertThat(savedUser).isEqualTo(Optional.empty());
    }

}