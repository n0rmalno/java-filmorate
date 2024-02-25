package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int generatedId = 0;
    private final Map<Integer, Film> storage = new HashMap<>();

    @Override
    public List<Film> getFilm() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void createFilm(Film film) {
        if (film.getId() == null || film.getId() == 0) {
            film.setId(++generatedId);
        }
        storage.put(film.getId(), film);
        log.info("Фильм добавлен");
    }

    @Override
    public void updateFilm(Film film) {
        if (storage.containsKey(film.getId())) {
            storage.put(film.getId(), film);
            log.info("Film обновлен");
        } else {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, "Такого id нет в фильмах");
        }

    }

    @Override
    public Film findByIdFilm(Integer id) {
        Film film = storage.get(id);
        if (film == null) {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, "Not found");
        }
        return film;
    }

    @Override
    public void saveFilm(Film film) {
        storage.put(film.getId(), film);
    }
}
