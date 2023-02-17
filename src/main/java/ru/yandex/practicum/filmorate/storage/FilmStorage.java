package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    ResponseEntity<Film> create(Film film);

    ResponseEntity<Film> update(Film film);

    ResponseEntity<List<Film>> readAll();

    ResponseEntity<Film> read(int id);

    ResponseEntity<?> delete(int id);

    ResponseEntity<?> addLikeToFilm(int filmId, int userId);

    ResponseEntity<?> deleteLikeFromFilm(int filmId, int userId);

    ResponseEntity<List<Film>> readMostLikedFilmsList(int count);

}
