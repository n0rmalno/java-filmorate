package ru.yandex.practicum.filmorate.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.DataNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.FilmorateValidationException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DataNotFoundException handleNotFoundException(DataNotFoundException e) {
        return new DataNotFoundException(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(FilmorateValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FilmorateValidationException handleNotFoundException(FilmorateValidationException e) {
        return new FilmorateValidationException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
