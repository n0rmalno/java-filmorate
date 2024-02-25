package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserStorage {

    User findByIdUser(Long id);

    void saveUser(User user);

    List<User> getUsers();

    void createUser(@Valid @RequestBody User user);

    void updateUser(@Valid @RequestBody User user);
}
