package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserStorage inMemoryUserStorage;

    @Autowired
    public UserService(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public ResponseEntity<User> addToFriends(int id, int friendId) {
        ResponseEntity<User> response = inMemoryUserStorage.addToFriends(id, friendId);
        log.info("Пользователи с id " + id + " и id " + friendId + " теперь друзья.");
        return response;
    }

    public ResponseEntity<?> deleteFromFriends(int id, int friendId) {
        ResponseEntity<?> response = inMemoryUserStorage.deleteFromFriends(id, friendId);
        log.info("Пользователи с id " + id + " и id " + friendId + " больше не друзья.");
        return response;
    }

    public ResponseEntity<List<User>> readFriendsList(int id) {
        ResponseEntity<List<User>> response = inMemoryUserStorage.readFriendsList(id);
        log.info("Возвращен список друзей пользователя с id " + id + ".");
        return response;
    }

    public ResponseEntity<List<User>> readMutualFriendsList(int id, int otherId) {
        ResponseEntity<List<User>> response = inMemoryUserStorage.readMutualFriendsList(id, otherId);
        log.info("Возвращен список общих друзей у пользователей с id " + id + " и id " + otherId + ".");
        return response;
    }

    public ResponseEntity<User> create(User user) {
        ResponseEntity<User> response = inMemoryUserStorage.create(user);
        log.info("Добавлен пользователь {}", user.getLogin());
        return response;
    }

    public ResponseEntity<User> update(User user) {
        ResponseEntity<User> response = inMemoryUserStorage.update(user);
        log.info("Обновлена информация о пользователе {}", user.getLogin());
        return response;
    }

    public ResponseEntity<List<User>> readAll() {
        ResponseEntity<List<User>> response = inMemoryUserStorage.readAll();
        log.info("Возвращен список всех пользователей в приложении.");
        return response;
    }

    public ResponseEntity<User> read(int id) {
        ResponseEntity<User> response = inMemoryUserStorage.read(id);
        log.info("Возвращен пользователь с id: {}", id);
        return response;
    }

    public UserStorage getInMemoryUserStorage() {
        return inMemoryUserStorage;
    }
}
