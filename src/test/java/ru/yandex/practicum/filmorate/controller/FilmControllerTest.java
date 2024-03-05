package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName(value = "Запрос на проверку пройден")
    public void createFilmTest() {
        Film film = Film.builder()
                .id(1)
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.of(1995, 1, 24))
                .duration(120)
                .build();
        ResponseEntity<Film> filmMap = testRestTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.OK, filmMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку пройден, но мы вводим пользователя без id")
    public void generateIdTest() {
        Film film = Film.builder()
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.of(1995, 1, 24))
                .duration(120)
                .build();
        ResponseEntity<Film> filmMap = testRestTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.OK, filmMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку не пройден из-за даты")
    public void releaseDateValidTest() {
        Film film = Film.builder()
                .id(1)
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.of(1800, 1, 1))
                .duration(120)
                .build();
        ResponseEntity<Film> filmMap = testRestTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, filmMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку не пройден из-за описания")
    public void descriptionValidTest() {
        Film film = Film.builder()
                .id(1)
                .name("Фильм")
                .description("В фильме рассказывается о приключениях главного героя Гарри Поттера, его друзей Рона Уизли" +
                        " и Гермионы Грейнджер, которые пытаются создать сопротивление Темному Лорду Волан-де-Морту и " +
                        "его приверженцам - пожирателями смерти. Они присоединяются к Ордену Феникса, секретной " +
                        "организации, созданной для борьбы с тёмными силами.")
                .releaseDate(LocalDate.of(1995, 1, 24))
                .duration(120)
                .build();
        ResponseEntity<Film> filmMap = testRestTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, filmMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку не пройден из-за продолжительности фильма")
    public void durationValidTest() {
        Film durationFail = Film.builder()
                .id(1)
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.of(1995, 1, 24))
                .duration(0)
                .build();
        ResponseEntity<Film> filmMap = testRestTemplate.postForEntity("/films", durationFail, Film.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, filmMap.getStatusCode());
    }
}