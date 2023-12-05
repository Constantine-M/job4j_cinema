package ru.job4j.cinema.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class SimpleFileService implements FileService {

    private final FileRepository fileRepository;

    public SimpleFileService(FileRepository sql2oFileRepository) {
        this.fileRepository = sql2oFileRepository;
    }

    /**
     * Данный метод позволяет найти
     * объект {@link FileDto} по его ID.
     *
     * Здесь мы используем метод
     * {@link  Optional#of}. Разница от
     * {@link  Optional#ofNullable} в том, что
     * Optional.of бросит исключение
     * NullPointerException, если ему передать
     * значение null в качестве параметра.
     *
     * Optional.ofNullable вернёт Optional,
     * не содержащий значение, если ему передать null.
     */
    @Override
    public Optional<FileDto> findById(int id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().path());
        return Optional.of(new FileDto(fileOptional.get().name(), content));
    }

    /**
     * Данный метод читает файл побайтово.
     *
     * @param path путь к файлу.
     * @return массив байт.
     */
    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
