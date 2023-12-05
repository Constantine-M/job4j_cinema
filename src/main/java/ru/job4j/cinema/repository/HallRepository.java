package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Hall;

import java.util.Collection;

/**
 * @author Constantine on 28.11.2023
 */
public interface HallRepository {

    Collection<Hall> findAll();

    Hall findById(int id);
}
