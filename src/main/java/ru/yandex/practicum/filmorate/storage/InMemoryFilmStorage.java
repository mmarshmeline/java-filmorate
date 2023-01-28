package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.EntityNotExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmorateValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int generatorFilmId;

    @Override
    public ResponseEntity<Film> create(Film film) {
        FilmorateValidator.validateFilm(film);
        if (films.containsKey(film.getId())) {
            throw new EntityAlreadyExistException("Этот фильм был добавлен ранее.");
        }
        film.setId(++generatorFilmId);
        films.put(generatorFilmId, film);
        return new ResponseEntity<>(film, HttpStatus.valueOf(201));
    }

    @Override
    public ResponseEntity<Film> update(Film film) {
        FilmorateValidator.validateFilm(film);
        if (!films.containsKey(film.getId())) {
            throw new EntityNotExistException("Такого фильма нет в приложении.");
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

    @Override
    public ResponseEntity<?> addLikeToFilm(int filmId, int userId) {
        if (films.containsKey(filmId)) { //нужно ли проверять наличие в программе пользователя
            return new ResponseEntity<>(films.get(filmId).getLikes().add(userId), HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого фильма нет в приложении.");
        }
    }

    @Override
    public ResponseEntity<?> deleteLikeFromFilm(int filmId, int userId) {
        if (filmId < 0 || userId < 0) {
            throw new EntityNotExistException("Неправильный id фильма или пользователя");
        }
        if (films.containsKey(filmId)) {
            return new ResponseEntity<>(films.get(filmId).getLikes().remove(userId), HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого фильма нет в приложении.");
        }
    }

    @Override
    public ResponseEntity<List<Film>> readMostLikedFilmsList(int count) {
        List<Film> mostLikedFilmsList = new ArrayList<>(films.values());
        Collections.sort(mostLikedFilmsList);
        mostLikedFilmsList = mostLikedFilmsList.stream().limit(count).collect(Collectors.toList());
        return new ResponseEntity<>(mostLikedFilmsList, HttpStatus.valueOf(200));
    }
}
