package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserRepository {

    User findByIdUser(Long id);

    void saveUser(User user);

    List<User> getUsers();

    void createUser(User user);

    void updateUser(User user);
}