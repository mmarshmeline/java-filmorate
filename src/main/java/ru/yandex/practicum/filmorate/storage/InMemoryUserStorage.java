package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    @Getter
    private final HashMap<Integer, User> users = new HashMap<>();
    private int generatorUserId;


    @Override
    public ResponseEntity<User> create(User user) {
        FilmorateValidator.validateUser(user);
        if (users.containsKey(user.getId())) {
            String warnMessage = "Этот пользователь уже был добавлен ранее.";
            log.warn(warnMessage);
            throw new EntityAlreadyExistException(warnMessage);
        }

        user.setId(++generatorUserId);
        users.put(generatorUserId, user);
        log.info("Добавлен пользователь {}", user.getLogin());
        return new ResponseEntity<>(user, HttpStatus.valueOf(201));
    }

    @Override
    public ResponseEntity<User> update(User user) {
        FilmorateValidator.validateUser(user);
        if (!users.containsKey(user.getId())) {
            String warnMessage = "Такого пользователя нет в приложении.";
            log.warn(warnMessage);
            throw new EntityNotExistException(warnMessage);
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
}
