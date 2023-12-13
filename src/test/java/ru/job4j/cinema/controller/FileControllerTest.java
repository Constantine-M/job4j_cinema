package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.job4j.cinema.service.file.FileService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {

    private FileService fileService;

    private FileController fileController;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    /**
     * Данный тест проверяет случай, когда
     * файл с определенным ID не найден.
     *
     * Сервер отправляет в этом случае код 404
     * и текст, сообщающий, что файл с
     * запрашиваемым ID не найден.
     */
    @Test
    public void whenRequestFileByIdThenGetResponseWithErrorMessage() {
        ResponseEntity<?> expectedResponseEntity = new ResponseEntity<>("File with id = 1 not found", HttpStatus.NOT_FOUND);
        when(fileService.findById(1)).thenReturn(Optional.empty());

        var actualResponseEntity = fileController.getById(1);

        assertThat(actualResponseEntity).isEqualTo(expectedResponseEntity);
    }

}