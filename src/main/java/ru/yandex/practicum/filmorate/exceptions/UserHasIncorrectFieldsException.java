package ru.yandex.practicum.filmorate.exceptions;

public class UserHasIncorrectFieldsException extends RuntimeException {
    public UserHasIncorrectFieldsException(String message) {
        super(message);
    }
}
