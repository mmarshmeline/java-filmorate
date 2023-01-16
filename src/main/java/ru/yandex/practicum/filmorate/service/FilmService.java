package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.EntityNotExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmorateValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int generatorFilmId;

    public Film create(Film film) {
        FilmorateValidator.validateFilm(film);

        if (films.containsKey(film.getId())) {
            String warnMessage = "Этот фильм был добавлен ранее.";
            log.warn(warnMessage);
            throw new EntityAlreadyExistException(warnMessage);
        }
        film.setId(++generatorFilmId);
        films.put(generatorFilmId, film);
        log.info("Добавлен новый фильм: {}", film.getName());
        return film;
    }

    public boolean update(Film film) {
        FilmorateValidator.validateFilm(film);

        if (!films.containsKey(film.getId())) {
            String warnMessage = "Такого фильма нет в приложении.";
            log.warn(warnMessage);
            throw new EntityNotExistException(warnMessage);
        }

        films.put(film.getId(), film);
        return true;
    }

    public List<Film> readAll () {
        return new ArrayList<>(films.values());
    }
}
