package ru.job4j.cinema.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Данный класс описывает объект сеанса кино,
 * в котором содержатся необходимые данные
 * для отображения на веб-странице.
 *
 Для демонстрации возможностей Lombok,
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
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
public class FilmSessionDto {

    private int id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int price;

    private String filmName;

    private int hallId;

    private String hallName;
}
