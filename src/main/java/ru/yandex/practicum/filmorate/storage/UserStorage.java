package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserStorage {

    ResponseEntity<User> create(User user);
    ResponseEntity<User> update(User user);
    ResponseEntity<List<User>> readAll ();
    ResponseEntity<User> read (int id);
    HashMap<Integer, User> getUsers();
}
