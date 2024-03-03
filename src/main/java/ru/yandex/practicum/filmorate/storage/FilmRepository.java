package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmRepository {
    Film findByIdFilm(Integer id); // Получение фильма по ID

    void saveFilm(Film film); //Сохранение

    List<Film> getFilms(); // Получение списка фильмов

    void createFilm(Film film); //тесты пройдены

    void updateFilm(Film film); //тесты пройдены
}