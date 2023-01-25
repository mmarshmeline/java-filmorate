package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;

public interface FilmStorage {

    ResponseEntity<Film> create(Film film);

    ResponseEntity<Film> update(Film film);

    ResponseEntity<List<Film>> readAll();

    ResponseEntity<Film> read(int id);

    ResponseEntity<?> delete(int id);

    HashMap<Integer, Film> getFilms();

}
