package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Getter
    private final UserStorage inMemoryUserStorage;

    @Autowired
    public UserService(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public ResponseEntity<User> addToFriends(int id, int friendId) {
        if (inMemoryUserStorage.getUsers().containsKey(id) && inMemoryUserStorage.getUsers().containsKey(friendId)) {
            //надо ли реагировать, если такой друг уже есть в друзьях
            inMemoryUserStorage.getUsers().get(id).getFriends().add(friendId);
            inMemoryUserStorage.getUsers().get(friendId).getFriends().add(id);
            return new ResponseEntity<>(inMemoryUserStorage.getUsers().get(friendId), HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого пользователя нет в приложении.");
        }
    }

    public ResponseEntity<?> deleteFromFriends(int id, int friendId) {
        if (inMemoryUserStorage.getUsers().containsKey(id) && inMemoryUserStorage.getUsers().containsKey(friendId)) {
            //надо ли реагировать, если такой друг уже есть в друзьях
            inMemoryUserStorage.getUsers().get(id).getFriends().remove(friendId);
            inMemoryUserStorage.getUsers().get(friendId).getFriends().remove(id);
            return new ResponseEntity<>("Дружба завершена.", HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого пользователя нет в приложении.");
        }
    }

    public ResponseEntity<List<User>> readFriendsList(int id) {
        if (inMemoryUserStorage.getUsers().containsKey(id)) {
            List<User> friendsList = new ArrayList<>();
            for (Integer element : inMemoryUserStorage.getUsers().get(id).getFriends()) {
                if (inMemoryUserStorage.getUsers().containsKey(element)) {
                    User friend = inMemoryUserStorage.getUsers().get(element);
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
        if (inMemoryUserStorage.getUsers().containsKey(id) && inMemoryUserStorage.getUsers().containsKey(otherId)) {
            for (Integer element : inMemoryUserStorage.getUsers().get(id).getFriends()) {
                if (inMemoryUserStorage.getUsers().get(otherId).getFriends().contains(element)) {
                    mutualFriendsList.add(inMemoryUserStorage.getUsers().get(element));
                }
            }
            return new ResponseEntity<>(mutualFriendsList, HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого пользователя нет в приложении.");
        }
    }
}
