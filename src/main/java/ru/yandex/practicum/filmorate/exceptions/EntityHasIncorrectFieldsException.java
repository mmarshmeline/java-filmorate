package ru.yandex.practicum.filmorate.exceptions;

public class EntityHasIncorrectFieldsException extends RuntimeException {
    public EntityHasIncorrectFieldsException(String message) {
        super(message);
    }
}
