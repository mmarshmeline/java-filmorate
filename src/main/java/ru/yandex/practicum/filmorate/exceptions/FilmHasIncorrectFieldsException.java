package ru.yandex.practicum.filmorate.exceptions;

public class FilmHasIncorrectFieldsException extends RuntimeException {
    public FilmHasIncorrectFieldsException(String message) {
        super(message);
    }
}
