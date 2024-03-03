package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmRepository implements FilmRepository {
    private int generatedId = 0;
    private final Map<Integer, Film> storage = new HashMap<>();

    @Override
    public List<Film> getFilms() { //тесты пройдены
        return new ArrayList<>(storage.values());
    }

    @Override
    public void createFilm(Film film) { //тесты пройдены
        film.setId(++generatedId);
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
    public Film findByIdFilm(Integer id) throws DataNotFoundException {  //тесты пройдены
        if (!storage.containsKey(id)) {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, "ID не может быть null");
        }
        return storage.get(id);
    }

    @Override
    public void saveFilm(Film film) {
        storage.put(film.getId(), film);
    }
}