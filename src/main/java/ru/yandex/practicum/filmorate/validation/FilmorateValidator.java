package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.EntityHasIncorrectFieldsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class FilmorateValidator {
    private static final int MAX_FILM_DESCRIPTION_LENGTH = 200;
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public static void validateFilm(Film film) {
        String validateErrorMessage;
        if (film.getName() == null || film.getName().isBlank()) {
            validateErrorMessage = "Название фильма не может быть пустым";
            log.warn(validateErrorMessage);
            throw new EntityHasIncorrectFieldsException(validateErrorMessage);
        }
        if (film.getDescription().length() > MAX_FILM_DESCRIPTION_LENGTH) {
            validateErrorMessage = "Максимальная длина описания фильма - 200 символов";
            log.warn(validateErrorMessage);
            throw new EntityHasIncorrectFieldsException(validateErrorMessage);
        }
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            validateErrorMessage = "Дата релиза не может быть раньше 28 декабря 1895 года.";
            log.warn(validateErrorMessage);
            throw new EntityHasIncorrectFieldsException(validateErrorMessage);
        }
        if (film.getDuration() < 0) {
            validateErrorMessage = "Продолжительность фильма должна быть положительной.";
            log.warn(validateErrorMessage);
            throw new EntityHasIncorrectFieldsException(validateErrorMessage);
        }
    }

    public static void validateUser(User user) {
        String validateErrorMessage;

        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            validateErrorMessage = "Электронная почта не может быть пустой и должна содержать символ @.";
            log.warn(validateErrorMessage);
            throw new EntityHasIncorrectFieldsException(validateErrorMessage);
        }
        if (user.getLogin().isBlank()) {
            validateErrorMessage = "Логин не может быть пустым и не может содержать пробелы.";
            log.warn(validateErrorMessage);
            throw new EntityHasIncorrectFieldsException(validateErrorMessage);
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            validateErrorMessage = "Дата рождения не может быть в будущем.";
            log.warn(validateErrorMessage);
            throw new EntityHasIncorrectFieldsException(validateErrorMessage);
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
