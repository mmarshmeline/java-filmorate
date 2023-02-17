package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.EntityNotExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.FilmorateValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int generatorUserId;


    @Override
    public ResponseEntity<User> create(User user) {
        FilmorateValidator.validateUser(user);
        if (users.containsKey(user.getId())) {
            throw new EntityAlreadyExistException("Этот пользователь уже был добавлен ранее.");
        }

        user.setId(++generatorUserId);
        users.put(generatorUserId, user);
        return new ResponseEntity<>(user, HttpStatus.valueOf(201));
    }

    @Override
    public ResponseEntity<User> update(User user) {
        FilmorateValidator.validateUser(user);
        if (!users.containsKey(user.getId())) {
            throw new EntityNotExistException("Такого пользователя нет в приложении.");
        }
        users.put(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.valueOf(200));
    }

    @Override
    public ResponseEntity<List<User>> readAll() {
        return new ResponseEntity<>(new ArrayList<>(users.values()), HttpStatus.valueOf(200));
    }

    @Override
    public ResponseEntity<User> read(int id) {
        if (users.containsKey(id)) {
            return new ResponseEntity<>(users.get(id), HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого пользователя нет.");
        }
    }

    @Override
    public ResponseEntity<User> addToFriends(int id, int friendId) {
        if (users.containsKey(id) && users.containsKey(friendId)) {
            //надо ли реагировать, если такой друг уже есть в друзьях
            users.get(id).getFriends().add(friendId);
            users.get(friendId).getFriends().add(id);
            return new ResponseEntity<>(users.get(friendId), HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого пользователя нет в приложении.");
        }
    }

    @Override
    public ResponseEntity<?> deleteFromFriends(int id, int friendId) {
        if (users.containsKey(id) && users.containsKey(friendId)) {
            //надо ли реагировать, если такой друг уже есть в друзьях
            users.get(id).getFriends().remove(friendId);
            users.get(friendId).getFriends().remove(id);
            return new ResponseEntity<>("Дружба завершена.", HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого пользователя нет в приложении.");
        }
    }

    @Override
    public ResponseEntity<List<User>> readFriendsList(int id) {
        if (users.containsKey(id)) {
            List<User> friendsList = new ArrayList<>();
            for (Integer element : users.get(id).getFriends()) {
                if (users.containsKey(element)) {
                    User friend = users.get(element);
                    friendsList.add(friend);
                }
            }
            return new ResponseEntity<>(friendsList, HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого пользователя нет в приложении.");
        }
    }

    public ResponseEntity<List<User>> readMutualFriendsList(int id, int otherId) {
        List<User> mutualFriendsList = new ArrayList<>();
        if (users.containsKey(id) && users.containsKey(otherId)) {
            for (Integer element : users.get(id).getFriends()) {
                if (users.get(otherId).getFriends().contains(element)) {
                    mutualFriendsList.add(users.get(element));
                }
            }
            return new ResponseEntity<>(mutualFriendsList, HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого пользователя нет в приложении.");
        }
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }
}
