package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    FilmStorage filmStorage;
    FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public List<Film> getFilm() {
        log.info("Гет всех фильмов");
        return filmService.getFilm();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос POST /films.");
        log.info("Попытка добавить фильм {}.", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос PUT /films.");
        log.info("Попытка добавить фильм {}.", film);
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        log.info("Получен запрос GET /films/{id}.");
        log.info("Попытка посмотреть фильм по id ", id);
        return filmStorage.findByIdFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addFilmPutsLike(@PathVariable Integer id, @PathVariable Long userId) {
        log.info("Получен запрос PUT /films/{id}/like/{userId}.");
        log.info("Попытка добавить лайка на фильм по id ", id);
        return filmService.addFilmPutsLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteFilmLike(@PathVariable Integer id, @PathVariable Long userId) {
        log.info("Получен запрос DELETE /films/{id}/like/{userId}.");
        log.info("Попытка удалить лайка на фильм по id ", id);
        filmService.deleteFilmLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularListFilms(@RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        log.info("Получен запрос GET /films/popular?count={count}.");
        log.info("Попытка посмотреть популярные фильмы в кол-ве ", count);
        return filmService.getPopularFilms(count);
    }
}