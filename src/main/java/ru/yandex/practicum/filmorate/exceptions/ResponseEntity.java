package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;

public class ResponseEntity extends Throwable {
    public ResponseEntity(HttpStatus status, String message) {
        super(message);
    }
}