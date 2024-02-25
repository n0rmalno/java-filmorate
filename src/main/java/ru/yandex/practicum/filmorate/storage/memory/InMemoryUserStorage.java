package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> storage = new HashMap<>();
    private Long generatedId = 0L;

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void createUser(User user) {
        if (user.getId() == null || user.getId() == 0) {
            generatedId++;
            user.setId(generatedId);
        }
        storage.put(user.getId(), user);
        log.info("Фильм добавлен");
    }

    @Override
    public void updateUser(User user) {
        if (storage.containsKey(user.getId())) {
            if (user.getId() == null || user.getId() == 0) {
                generatedId++;
                user.setId(generatedId);
            }
            storage.replace(user.getId(), user);
            log.info("User обновлен");
        } else {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, "Такого id нет в User");
        }

    }

    @Override
    public User findByIdUser(Long id) {
        User user = storage.get(id);
        if (user == null) {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, "Not found");
        }
        return user;
    }

    public void saveUser(User user) {
        storage.put(user.getId(), user);
    }
}