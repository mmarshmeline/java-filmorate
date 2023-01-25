package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) { // внесение пользователя в базу приложения
        userService.getInMemoryUserStorage().create(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        userService.getInMemoryUserStorage().update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> readAll() {
        return userService.getInMemoryUserStorage().readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> read(@PathVariable int id) {
        return userService.getInMemoryUserStorage().read(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> addToFriend(@PathVariable int id, @PathVariable int friendId) { //добавление в друзья
        return userService.addToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> deleteFromFriends(@PathVariable int id, @PathVariable int friendId) { // удаление из друзей
        return userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<?> readFriendsList(@PathVariable int id) { //возвращаем список пользователей, являющихся его друзьями
        return userService.readFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<?> readMutualFriendsList(@PathVariable int id, @PathVariable int otherId) { //возвращаем список друзей, общих с др.пользователем
        return userService.readMutualFriendsList(id, otherId);
    }
}
