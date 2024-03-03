package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exceptions.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserRepository;

import java.util.*;

@Service
@Slf4j
@Validated
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        validate(user);
        userRepository.createUser(user);
        return user;
    }

    public User updateUser(User user) {
        validate(user);
        userRepository.updateUser(user);
        return user;
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User findByIdUser(Long id) {
        return userRepository.findByIdUser(id);
    }

    public void addFriend(Long id, Long friendId) { //тесты пройдены
        User user = userRepository.findByIdUser(id);
        User userFriend = userRepository.findByIdUser(friendId);

        if (userFriend != null) {
            if (userFriend.getFriendsId() == null) {
                userFriend.setFriendsId(new HashSet<>());
            }
            Set<Long> friendsUser = new HashSet<>(userFriend.getFriendsId());
            friendsUser.add(id);
            log.info("Добавили для Юзера по id " + friendId + " друга по id " + id);
            userFriend.setFriendsId(friendsUser);
            userRepository.saveUser(userFriend); // Сохраняем обновленные данные userFriend
        }

        if (user != null) {
            if (user.getFriendsId() == null) {
                user.setFriendsId(new HashSet<>());
            }
            Set<Long> friends = new HashSet<>(user.getFriendsId());
            friends.add(friendId);
            log.info("Добавили для Юзера по id " + id + " друга по id " + friendId);
            user.setFriendsId(friends);
            userRepository.saveUser(user); // Сохраняем обновленные данные user
        }
    }

    public void deleteFriend(Long id, Long friendId) {
        User user = userRepository.findByIdUser(id);

        if (user == null) {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, "Not found");
        }
        if (user.getFriendsId() == null) {
            user.setFriendsId(new HashSet<>());
        }

        Set<Long> friends = new HashSet<>(user.getFriendsId());
        friends.remove(friendId);
        log.info("Удалили для Юзера по id " + id + " друга по id " + friendId);
        user.setFriendsId(friends);
    }

    public List<User> getAllFriendsUserId(Long id) { //тесты пройдены
        User user = userRepository.findByIdUser(id);
        Set<Long> friends = user.getFriendsId();

        List<User> friendUsers = new ArrayList<>();
        for (Long friendId : friends) {
            User friendUser = userRepository.findByIdUser(friendId);
            if (friendUser != null) {
                friendUsers.add(friendUser);
            }
        }
        log.info("Показали всех друзей Юзера по id " + id);
        return friendUsers;
    }

    public List<User> getAllCommonFriends(Long id, Long otherId) { //тесты пройдены
        User user = userRepository.findByIdUser(id);
        User otherUser = userRepository.findByIdUser(otherId);

        if (user.getFriendsId() == null || otherUser.getFriendsId() == null) {
            return Collections.emptyList();
        }
        Set<Long> userFriendsId = user.getFriendsId();
        Set<Long> otherUserFriendsId = otherUser.getFriendsId();
        List<User> commonFriends = new ArrayList<>();

        for (Long userId : userFriendsId) {
            if (otherUserFriendsId.contains(userId)) {
                User commonFriend = userRepository.findByIdUser(userId);
                if (commonFriend != null) {
                    commonFriends.add(commonFriend);
                }
            }
        }

        log.info("Показали всех общих друзей Юзера по id " + id + " и " + otherId);
        return commonFriends;
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}