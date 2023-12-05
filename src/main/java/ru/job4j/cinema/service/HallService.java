package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Hall;

import java.util.Collection;

/**
 * @author Constantine on 03.12.2023
 */
public interface HallService {

    Collection<Hall> findAll();

    Hall findById(int id);
}
