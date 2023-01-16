package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.EntityNotExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.FilmorateValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int generatorUserId;

    public User create (User user) {
        FilmorateValidator.validateUser(user);
        if (users.containsKey(user.getId())) {
            String warnMessage = "Этот пользователь уже был добавлен ранее.";
            log.warn(warnMessage);
            throw new EntityAlreadyExistException(warnMessage);
        }

        user.setId(++generatorUserId);
        users.put(generatorUserId, user);
        log.info("Добавлен пользователь {}", user.getLogin());
        return user;
    }

    public boolean update (User user) {
        FilmorateValidator.validateUser(user);
        if (!users.containsKey(user.getId())) {
            String warnMessage = "Такого пользователя нет в приложении.";
            log.warn(warnMessage);
            throw new EntityNotExistException(warnMessage);
        }
        users.put(user.getId(), user);
        return true;
    }

    public List<User> readAll() {
        return new ArrayList<>(users.values());
    }
}
