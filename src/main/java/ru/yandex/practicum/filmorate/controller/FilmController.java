package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<Film> create(@RequestBody Film film) { // внесение фильма в базу приложения
        filmService.create(film);
        return new ResponseEntity<>(film, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Film> update(@RequestBody Film film) { // обновление данных по уже внесенному в базу приложения фильму
        filmService.update(film);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Film>> readAll() { // получение списка всех фильмов, ранее внесенных в базу приложения
        return filmService.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> read(@PathVariable int id) {
        return filmService.read(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<?> addLikeToFilm(@PathVariable(value = "id") int filmId, @PathVariable int userId) {
        return filmService.addLikeToFilm(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<?> deleteLikeFromFilm(@PathVariable(value = "id") int filmId, @PathVariable int userId) {
        return filmService.deleteLikeFromFilm(filmId, userId);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> readMostLikedFilmsList(@RequestParam(defaultValue = "10", required = false) int count) {
        return filmService.readMostLikedFilmsList(count);
    }
}
