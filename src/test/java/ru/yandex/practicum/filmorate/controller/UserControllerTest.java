package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    private UserController userController;
    private User user;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
        user = User.builder()
                .email("shubaca@mail.ru")
                .login("shubaca")
                .name("Alex")
                .birthday(LocalDate.ofEpochDay(1995 - 1 - 24))
                .build();
        userController.create(user);
    }

    @Test
    public void validateUserPositive() {
        userController.validate(user);
    }

    @Test
    public void validateUserNegative() {
        User user1 = User.builder()
                .email("shubaca@mail.ru")
                .login("shubaca")
                .name(" ")
                .birthday(LocalDate.ofEpochDay(1995 - 1 - 24))
                .build();
        userController.validate(user1);
        assertEquals(user1.getName(), "shubaca");
    }

    @Test
    public void createUser() {
        assertEquals(userController.getAll().size(), 1);
    }

    @Test
    public void updateUser() {
        user.setEmail("despasito@mail.ru");
        userController.update(user);

        for (User test : userController.getAll()) {
            assertEquals(test, user);
            assertEquals(test.getEmail(), "despasito@mail.ru");
        }
    }

    @Test
    public void equalityUser() {

        for (User user1 : userController.getAll()) {
            assertEquals(user1.getId(), 1);
            assertEquals(user1.getEmail(), "shubaca@mail.ru");
            assertEquals(user1.getLogin(), "shubaca");
            assertEquals(user1.getName(), "Alex");
            assertEquals(user1.getBirthday(), LocalDate.ofEpochDay(1995 - 1 - 24));
        }
    }
}