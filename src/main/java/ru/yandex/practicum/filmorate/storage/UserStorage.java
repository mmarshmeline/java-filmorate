package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserStorage {

    ResponseEntity<User> create(User user);

    ResponseEntity<User> update(User user);

    ResponseEntity<List<User>> readAll();

    ResponseEntity<User> read(int id);

    ResponseEntity<User> addToFriends(int id, int friendId);

    ResponseEntity<?> deleteFromFriends(int id, int friendId);

    ResponseEntity<List<User>> readFriendsList(int id);

    ResponseEntity<List<User>> readMutualFriendsList(int id, int otherId);

    HashMap<Integer, User> getUsers();
}
