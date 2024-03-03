package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exceptions.DataNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.MethodArgumentNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmRepository;
import ru.yandex.practicum.filmorate.storage.UserRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Validated
public class FilmService {
    private final FilmRepository filmRepository; // тесты пройдены
    private final UserRepository userRepository; // тесты пройдены
    private static final LocalDate START_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(FilmRepository filmRepository, UserRepository userRepository) {
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    public Film createFilm(Film film) {
        validate(film);
        filmRepository.createFilm(film);
        return film;
    }

    public Film updateFilm(Film film) {
        validate(film);
        filmRepository.updateFilm(film);
        return film;
    }

    public Film findByIdFilm(Integer id) {
        return filmRepository.findByIdFilm(id);
    }

    public List<Film> getFilms() { // тесты пройдены
        return filmRepository.getFilms();
    } // тесты пройдены

    public Film addFilmPutsLike(Integer id, Long userId) { //тесты пройдены
        User user = userRepository.findByIdUser(userId);
        Film film = filmRepository.findByIdFilm(id);

        Set<Integer> likes = film.getLikedUserIds();
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.add(Math.toIntExact(user.getId()));
        film.setLikedUserIds(likes);

        filmRepository.saveFilm(film);
        log.info("Добавили лайк на фильм под id " + id);
        return film;
    }

    public void deleteFilmLikedUserIds(Integer id, Long userId) { //тесты пройдены
        User user;
        Film film;
        validationId(id, userId);

        try {
            user = userRepository.findByIdUser(userId);
            film = filmRepository.findByIdFilm(id);
        } catch (DataNotFoundException e) {
            log.warn("");
            return;
        }

        Set<Integer> likes = film.getLikedUserIds();

        if (likes.isEmpty()) {
            return;
        }

        likes.remove(user.getId());
        film.setLikedUserIds(likes);
        log.info("Удалили лайк на фильме под id " + id);
        filmRepository.saveFilm(film);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        Collection<Film> filmCollection = filmRepository.getFilms();

        if (filmCollection == null) {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, "Not found");
        }

        List<Film> popularFilms = filmCollection.stream()
                .filter(film -> (film).getLikedUserIds() != null)
                .sorted(Comparator.comparingInt(film -> ((Film) film).getLikedUserIds().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
        log.info("Показали популярные фильмы");
        return popularFilms;
    }

    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(START_RELEASE_DATE)) {
            log.debug("Не пройдена валидация releaseDate: {}", film.getReleaseDate());

            throw new MethodArgumentNotValidException(HttpStatus.BAD_REQUEST,
                    "Параметр ReleaseDate не должна быть не раньше даты 1895.12.28");
        }
    }

    private void validationId(Integer id, Long userId) { //тесты пройдены
        if (id < 0 || userId < 0) {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, "Not found");
        }
    }
}