package ru.yandex.practicum.filmorate.exceptions;

public class FilmorateValidationException extends RuntimeException {
    public FilmorateValidationException(String message) {
        super(message);
    }
}
