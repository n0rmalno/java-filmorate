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

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }

    @Test
    void validateFilmPositive() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.of(1900, 1, 1))
                .duration(100)
                .build();
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
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.of(1910, 1, 1))
                .duration(100)
                .build();
        filmController.create(film);
        assertEquals(filmController.getAll().size(), 1);
    }

    @Test
    void updateFilm() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.of(1910, 1, 1))
                .duration(100)
                .build();
        filmController.create(film);
        film.setDescription("Description update");
        filmController.update(film);

        for (Film test : filmController.getAll()) {
            assertEquals(test, film);
        }
    }

    @Test
    public void qualityFilm() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.of(1910, 1, 1))
                .duration(100)
                .build();
        filmController.create(film);

        for (Film film1 : filmController.getAll()) {
            assertEquals(film1.getId(), 1);
            assertEquals(film1.getName(), "Name");
            assertEquals(film1.getDescription(), "Description");
            assertEquals(film1.getReleaseDate(), LocalDate.of(1910, 1, 1));
            assertEquals(film1.getDuration(), 100);
        }
    }
}