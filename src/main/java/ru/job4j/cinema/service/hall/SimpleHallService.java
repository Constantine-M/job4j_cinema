package ru.job4j.cinema.service.hall;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.hall.HallRepository;

import java.util.Collection;

/**
 * @author Constantine on 03.12.2023
 */
@Service
public class SimpleHallService implements HallService {

    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Collection<Hall> findAll() {
        return hallRepository.findAll();
    }

    @Override
    public Hall findById(int id) {
        return hallRepository.findById(id);
    }
}
