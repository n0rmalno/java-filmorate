package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(HttpStatus status, String message) {
        super(message);
    }
}
