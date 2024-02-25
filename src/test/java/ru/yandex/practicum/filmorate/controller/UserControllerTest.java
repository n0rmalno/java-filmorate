package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName(value = "Запрос на проверку пройден")
    public void createUserTest() {
        User user = User.builder()
                .id(1L)
                .email("mail@yandex.ru")
                .login("doloreUpdate")
                .name("est adipisicing")
                .birthday(LocalDate.of(2004, 1, 20))
                .build();
        ResponseEntity<User> userMap = testRestTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.OK, userMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку пройден, но мы вводим пользователя без id")
    public void testUserWithoutIdRequest() {
        User user = User.builder()
                .email("shubaca@mail.ru")
                .login("n0rmalno")
                .name("Alex")
                .birthday(LocalDate.of(1995, 1, 24))
                .build();
        ResponseEntity<User> userMap = testRestTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.OK, userMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на не проверку пройден из-за email")
    public void testUserMainChangedValidRequest() {
        User user = User.builder()
                .id(1L)
                .email("shubacamail.ru")
                .login("n0rmalno")
                .name("Alex")
                .birthday(LocalDate.of(1995, 1, 24))
                .build();
        ResponseEntity<User> userMap = testRestTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, userMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку пройден, но поменялось name на login")
    public void noNameValidateTest() {
        User noName = User.builder()
                .id(1L)
                .email("shubaca@mail.ru")
                .login("n0rmalno")
                .name("")
                .birthday(LocalDate.of(1995, 1, 24))
                .build();
        ResponseEntity<User> userMap = testRestTemplate.postForEntity("/users", noName, User.class);
        User userBody = userMap.getBody();
        assertEquals(userBody.getName(), "n0rmalno");
    }
}