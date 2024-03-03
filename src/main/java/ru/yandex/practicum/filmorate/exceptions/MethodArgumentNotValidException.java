package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;

public class MethodArgumentNotValidException extends RuntimeException {
    public MethodArgumentNotValidException(HttpStatus status, String message) {
        super(message);
    }
}
