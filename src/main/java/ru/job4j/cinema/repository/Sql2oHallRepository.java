package ru.job4j.cinema.repository;


import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Hall;

import java.util.Collection;

/**
 * @author Constantine on 28.11.2023
 */
@Repository
public class Sql2oHallRepository implements HallRepository {


    /**
     * Sql2o - небольшая и быстрая библиотека
     * для доступа к реляционным базам данных.
     * По сути это клиент для работы с БД.
     */
    private final Sql2o sql2o;

    public Sql2oHallRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<Hall> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from halls");
            return query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetch(Hall.class);
        }
    }

    @Override
    public Hall findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from halls where id = :id")
                    .addParameter("id", id);
            return query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetchFirst(Hall.class);
        }
    }
}
