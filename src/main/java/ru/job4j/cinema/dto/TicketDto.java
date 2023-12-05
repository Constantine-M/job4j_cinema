package ru.job4j.cinema.dto;

import java.time.LocalDateTime;

/**
 * Данный класс описывает DTO (билет).
 *
 * Он был создан, чтобы всю необходимую
 * информацию можно было взять из одного
 * объекта. Используется на странице,
 * появляющейся после успешного заказа.
 *
 * @author Constantine on 29.11.2023
 */
public class TicketDto {

    private final String filmName;

    private final LocalDateTime startTime;

    private final LocalDateTime endTime;

    private final int hallId;

    private final int rowNumber;

    private final int seatNumber;

    private final int price;

    private TicketDto(TicketDtoBuilder builder) {
        this.filmName = builder.filmName;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.hallId = builder.hallId;
        this.rowNumber = builder.rowNumber;
        this.seatNumber = builder.seatNumber;
        this.price = builder.price;
    }

    public String filmName() {
        return filmName;
    }

    public LocalDateTime startTime() {
        return startTime;
    }

    public LocalDateTime endTime() {
        return endTime;
    }

    public int hallId() {
        return hallId;
    }

    public int rowNumber() {
        return rowNumber;
    }

    public int seatNumber() {
        return seatNumber;
    }

    public int price() {
        return price;
    }

    /**
     * Данный класс позволяет создавать
     * различные комбинации нужного
     * объекта. То есть, во время создания
     * объекта мы можем указать только
     * те поля, которые нам требуются.
     *
     * Еще данный способ удобен тем, что
     * нам не придется задумываться над
     * тем, в каком порядке прописывать
     * параметры объекта во время его создания.
     *
     * Здесь мы применили шаблон
     * проектирования "Строитель" (в народе
     * "билдер").
     */
    public static class TicketDtoBuilder {

        private String filmName;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private int hallId;
        private int rowNumber;
        private int seatNumber;
        private int price;

        public TicketDtoBuilder() {
            super();
        }
        public TicketDtoBuilder filmName(String filmName) {
            this.filmName = filmName;
            return this;
        }

        public TicketDtoBuilder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public TicketDtoBuilder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public TicketDtoBuilder hallId(int hallId) {
            this.hallId = hallId;
            return this;
        }

        public TicketDtoBuilder rowNumber(int rowNumber) {
            this.rowNumber = rowNumber;
            return this;
        }
        public TicketDtoBuilder seatNumber(int seatNumber) {
            this.seatNumber = seatNumber;
            return this;
        }
        public TicketDtoBuilder price(int price) {
            this.price = price;
            return this;
        }
        public TicketDto build() {
            return new TicketDto(this);
        }
    }
}
