package ru.job4j.cinema.repository.hall;

import ru.job4j.cinema.model.Hall;

import java.util.Collection;

/**
 * @author Constantine on 28.11.2023
 */
public interface HallRepository {

    Collection<Hall> findAll();

    Hall findById(int id);
}
