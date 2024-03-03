package ru.yandex.practicum.filmorate.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.DataNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.MethodArgumentNotValidException;
import ru.yandex.practicum.filmorate.exceptions.ResponseEntity;

@Slf4j
@RestControllerAdvice
public class ErrorHandler { //тесты пройдены
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DataNotFoundException handleNotFoundException(DataNotFoundException e) {
        log.debug("Получен статус 404 Not found: {}", e.getMessage(), e);
        return new DataNotFoundException(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ResponseEntity.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleServerError(ResponseEntity e) {
        log.debug("Получен статус 500 Not found: {}", e.getMessage(), e);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MethodArgumentNotValidException handleValidationException(MethodArgumentNotValidException e) {
        log.debug("Получен статус 400 Not found: {}", e.getMessage(), e);
        return new MethodArgumentNotValidException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
