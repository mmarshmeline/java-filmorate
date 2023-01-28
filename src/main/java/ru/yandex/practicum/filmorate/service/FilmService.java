package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public ResponseEntity<?> addLikeToFilm(int filmId, int userId) {
        ResponseEntity<?> result = inMemoryFilmStorage.addLikeToFilm(filmId, userId);
        log.info("Пользователем с id " + userId + " поставлен лайк фильму с id " + filmId);
        return result;
    }

    public ResponseEntity<?> deleteLikeFromFilm(int filmId, int userId) {
        ResponseEntity<?> result = inMemoryFilmStorage.deleteLikeFromFilm(filmId, userId);
        log.info("Пользователем с id " + userId + " снят лайк с фильма с id " + filmId);
        return result;
    }

    public ResponseEntity<List<Film>> readMostLikedFilmsList(int count) {
        ResponseEntity<List<Film>> result = inMemoryFilmStorage.readMostLikedFilmsList(count);
        log.info("Топ-" + count + " фильмов");
        return result;
    }

    public ResponseEntity<Film> create(Film film) {
        ResponseEntity<Film> result = inMemoryFilmStorage.create(film);
        log.info("Добавлен новый фильм: {}", film.getName());
        return result;
    }

    public ResponseEntity<Film> update(Film film) {
        ResponseEntity<Film> result = inMemoryFilmStorage.update(film);
        log.info("Обновлены данные о фильме: {}", film.getName());
        return result;
    }

    public ResponseEntity<List<Film>> readAll() {
        ResponseEntity<List<Film>> result = inMemoryFilmStorage.readAll();
        log.info("Возвращен список всех фильмов приложения.");
        return result;
    }

    public ResponseEntity<Film> read(int id) {
        ResponseEntity<Film> result = inMemoryFilmStorage.read(id);
        log.info("Возвращен фильм с id: {}", id);
        return result;
    }

    public ResponseEntity<?> delete(int id) {
        ResponseEntity<?> result = inMemoryFilmStorage.delete(id);
        log.info("Удален фильм с id: {}", id);
        return result;
    }
}
