package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Genre;

import java.util.Collection;

@Repository
public class Sql2oGenreRepository implements GenreRepository {

    /**
     * Sql2o - небольшая и быстрая библиотека
     * для доступа к реляционным базам данных.
     * По сути это клиент для работы с БД.
     */
    private final Sql2o sql2o;

    public Sql2oGenreRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<Genre> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from genres");
            return query.setColumnMappings(Genre.COLUMN_MAPPING).executeAndFetch(Genre.class);
        }
    }

    @Override
    public Genre findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from genres where id = :id")
                    .addParameter("id", id);
            return query.executeAndFetchFirst(Genre.class);
        }
    }
}
