package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;

public class FilmorateValidationException extends RuntimeException {
    public FilmorateValidationException(HttpStatus status, String message) {
        super(message);
    }
}
