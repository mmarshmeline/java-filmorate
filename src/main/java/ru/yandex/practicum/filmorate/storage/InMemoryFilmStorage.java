package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.EntityNotExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmorateValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    @Getter
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int generatorFilmId;

    @Override
    public ResponseEntity<Film> create(Film film) {
        FilmorateValidator.validateFilm(film);
        if (films.containsKey(film.getId())) {
            String warnMessage = "Этот фильм был добавлен ранее.";
            log.warn(warnMessage);
            throw new EntityAlreadyExistException(warnMessage);
        }
        film.setId(++generatorFilmId);
        films.put(generatorFilmId, film);
        log.info("Добавлен новый фильм: {}", film.getName());
        return new ResponseEntity<>(film, HttpStatus.valueOf(201));
    }

    @Override
    public ResponseEntity<Film> update(Film film) {
        FilmorateValidator.validateFilm(film);
        if (!films.containsKey(film.getId())) {
            String warnMessage = "Такого фильма нет в приложении.";
            log.warn(warnMessage);
            throw new EntityNotExistException(warnMessage);
        }
        films.put(film.getId(), film);
        return new ResponseEntity<>(film, HttpStatus.valueOf(200));
    }

    @Override
    public ResponseEntity<List<Film>> readAll() {
        return new ResponseEntity<>(new ArrayList<>(films.values()), HttpStatus.valueOf(200));
    }

    @Override
    public ResponseEntity<Film> read(int id) {
        if (films.containsKey(id)) {
            return new ResponseEntity<>(films.get(id), HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого фильма нет в приложении.");
        }
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        if (films.containsKey(id)) {
            return new ResponseEntity<>(films.remove(id), HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого фильма нет в приложении.");
        }
    }
}
