package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getFilm() {
        log.info("Гет всех фильмов");
        return filmService.getFilms();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос POST /films.");
        log.info("Попытка добавить фильм: {}.", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос PUT /films.");
        log.info("Попытка обновить фильм: {}.", film);
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film findByIdFilm(@PathVariable Integer id) {
        log.info("Получен запрос GET /films/{id}.");
        log.info("Попытка посмотреть фильм под id: {} ", id);
        return filmService.findByIdFilm(id); // тесты пройдены
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addFilmPutsLike(@PathVariable Integer id, @PathVariable Long userId) {
        log.info("Получен запрос PUT /films/{id}/like/{userId}.");
        log.info("Попытка добавить лайк на фильм по id: {}", id);
        return filmService.addFilmPutsLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteFilmLike(@PathVariable Integer id, @PathVariable Long userId) {
        log.info("Получен запрос DELETE /films/{id}/like/{userId}.");
        log.info("Попытка удалить лайк на фильме под id: {} ", id);
        filmService.deleteFilmLikedUserIds(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularListFilms(@RequestParam(value = "count", defaultValue = "10")
                                                @Positive Integer count) {
        log.info("Получен запрос GET /films/popular?count={count}.");
        log.info("Попытка посмотреть популярные фильмы в кол-ве: {}", count);
        return filmService.getPopularFilms(count);
    }
}