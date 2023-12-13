package ru.job4j.cinema.repository.file;

import ru.job4j.cinema.model.File;

import java.util.Optional;

/**
 * Данный интерфейс описывает
 * работу с файлами.
 */
public interface FileRepository {

    Optional<File> findById(int id);
}
