package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oUserRepository implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oUserRepository.class);

    /**
     * Sql2o - небольшая и быстрая библиотека
     * для доступа к реляционным базам данных.
     * По сути это клиент для работы с БД.
     */
    private final Sql2o sql2o;


    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Данный метод сохраняет пользователя
     * в базу данных.
     *
     * Т.к. пользователь должен быть уникальным
     * (уникальность достигается за счет
     * уникальной почты), мы ловим исключение
     * и вручную обрабатываем, чтобы на проде
     * не получить ошибку. Таким образом,
     * в случае, когда почта уже занята,
     * мы вернем пустой Optional.
     *
     * В процессе добавления параметра,
     * обращай внимание на имена. Например,
     * в блоке создания запроса мы в кач-ве
     * параметров добавляли имя, почту и пароль.
     * Параметр fullName должен быть в sql запросе,
     * в values и он же будет в параметре запроса.
     */
    @Override
    public Optional<User> save(User user) {
        try (var connection = sql2o.open()) {
            var sql = """
                    insert into users(full_name, email, password)
                    values (:fullName, :email, :password)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("fullName", user.fullName())
                    .addParameter("email", user.email())
                    .addParameter("password", user.password());
            int generatedId = query.setColumnMappings(User.COLUMN_MAPPING).executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Sql2oException e) {
            LOG.error("SQL EXCEPTION LOGGED: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = sql2o.open()) {
            var sql = """
                    select * from users
                    where email = :email and password = :password
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("email", email)
                    .addParameter("password", password);
            var user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Collection<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users");
            return query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users WHERE id = :id")
                    .addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }
}
