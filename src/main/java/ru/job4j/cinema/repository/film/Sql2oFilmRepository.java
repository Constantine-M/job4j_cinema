package ru.job4j.cinema.repository.film;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Film;

import java.util.Collection;

@Repository
public class Sql2oFilmRepository implements FilmRepository {

    /**
     * Sql2o - небольшая и быстрая библиотека
     * для доступа к реляционным базам данных.
     * По сути это клиент для работы с БД.
     */
    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<Film> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }

    @Override
    public Film findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from films where id = :id")
                    .addParameter("id", id);
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
        }
    }
}
