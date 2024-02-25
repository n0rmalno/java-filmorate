package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;

public interface FilmStorage {
    Film findByIdFilm(Integer id); // Получение фильма по ID

    void saveFilm(Film film); //Сохранение

    List<Film> getFilm(); // Получение списка фильмов

    void createFilm(@Valid @RequestBody Film film);

    void updateFilm(@Valid @RequestBody Film film); // Обновление
}
