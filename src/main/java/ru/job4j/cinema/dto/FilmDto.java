package ru.job4j.cinema.dto;

import lombok.*;

/**
 * Данный класс описывает объект-фильм,
 * в котором содержатся необходимые данные
 * для отображения на веб-странице.
 *
 * Для демонстрации возможностей Lombok,
 * были сгенерированы конструкторы,
 * геттеры, сеттеры и билдер.
 *
 * Удалена часть кода, оторая описывает
 * шаблон проектирования "Строитель".
 * Мы его использовали для преобразования
 * Entity --> DTO и в тестах.
 * Сейчас для преобразования используем
 * MapStruct, но в тестах билдер
 * по-прежнему удобен.
 *
 * Таким образом,
 * можно наглядно увидеть разницу между
 * количеством кода в даном классе и
 * классе {@link TicketDto}, где не были
 * использованы Lombok и MapStruct.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "of")
public class FilmDto {

    private int id;

    private String name;

    private String description;

    private int year;

    private int minimalAge;

    private int durationInMinutes;

    private String genre;

    private int fileId;
}
