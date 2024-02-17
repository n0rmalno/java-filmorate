package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.FilmorateValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmControllerTest {

    private FilmController filmController;
    private Film film;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
        film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.of(1900, 1, 1))
                .duration(100)
                .build();
        filmController.create(film);
    }

    @Test
    void validateFilmPositive() {
        filmController.validate(film);
    }

    @Test
    void validateFilmNegative() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.of(1800, 1, 1))
                .duration(100)
                .build();
        Assertions.assertThrows(FilmorateValidationException.class, () -> filmController.validate(film));
    }

    @Test
    void createFilm() {
        assertEquals(filmController.getAll().size(), 1);
    }

    @Test
    void updateFilm() {
        film.setDescription("Description update");
        filmController.update(film);

        for (Film test : filmController.getAll()) {
            assertEquals(test, film);
        }
    }

    @Test
    public void qualityFilm() {

        for (Film film1 : filmController.getAll()) {
            assertEquals(film1.getId(), 1);
            assertEquals(film1.getName(), "Name");
            assertEquals(film1.getDescription(), "Description");
            assertEquals(film1.getReleaseDate(), LocalDate.of(1900, 1, 1));
            assertEquals(film1.getDuration(), 100);
        }
    }
}