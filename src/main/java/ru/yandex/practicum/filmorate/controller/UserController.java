package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    UserService userService;
    UserStorage userStorage;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Гет всех юзеров");
        return userService.getUser();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.debug("Получен запрос POST /users.");
        log.debug("Попытка добавить пользователя {}.", user);
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.debug("Получен запрос PUT /users.");
        log.debug("Попытка добавить пользователя {}.", user);
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public User findByIdUser(@PathVariable Long id) {
        log.debug("Получен запрос GET /users/{id}.");
        log.debug("Попытка посмотреть пользователя по id ", id);
        return userService.findByIdUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("Получен запрос PUT /users/{id}/friends/{friendId}.");
        log.debug("Попытка добавить друга по id ", friendId, "для пользователя по id ", id);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("Получен запрос DELETE /users/{id}/friends/{friendId}.");
        log.debug("Попытка удалить друга по id ", friendId, "для пользователя по id ", id);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Long id) {
        log.debug("Получен запрос GET /users/{id}/friends.");
        log.debug("Попытка показать всех друзей по id юзера ", id);
        return userService.getAllFriendsUserId(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getAllCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.debug("Получен запрос GET /users/{id}/friends/common/{otherId}.");
        log.debug("Попытка показать общих друзей юзера по id ", id, "и юзера по id ", otherId);
        return userService.getAllCommonFriends(id, otherId);
    }
}