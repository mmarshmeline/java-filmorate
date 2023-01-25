package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    @Getter
    private final FilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public ResponseEntity<?> addLikeToFilm (int filmId, int userId) {
        if (inMemoryFilmStorage.getFilms().containsKey(filmId)) { //нужно ли проверять наличие в программе пользователя
            return new ResponseEntity<>(inMemoryFilmStorage.getFilms().get(filmId).getLikes().add(userId), HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого фильма нет в приложении.");
        }
    }

    public ResponseEntity<?> deleteLikeFromFilm (int filmId, int userId) {
        if (filmId < 0 || userId < 0) {
            throw new EntityNotExistException("Неправильный id фильма или пользователя");
        }
        if (inMemoryFilmStorage.getFilms().containsKey(filmId)) {
            return new ResponseEntity<>(inMemoryFilmStorage.getFilms().get(filmId).getLikes().remove(userId), HttpStatus.valueOf(200));
        } else {
            throw new EntityNotExistException("Такого фильма нет в приложении.");
        }
    }

    public ResponseEntity<List<Film>> readMostLikedFilmsList (int count) {
        List<Film> mostLikedFilmsList = new ArrayList<>(inMemoryFilmStorage.getFilms().values());
        Collections.sort(mostLikedFilmsList);
        mostLikedFilmsList = mostLikedFilmsList.stream().limit(count).collect(Collectors.toList());
        return new ResponseEntity<>(mostLikedFilmsList, HttpStatus.valueOf(200));
    }
}
