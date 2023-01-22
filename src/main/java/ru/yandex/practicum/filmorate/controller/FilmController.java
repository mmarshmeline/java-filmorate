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
    public ResponseEntity <Film> create(@RequestBody Film film) { // внесение фильма в базу приложения
        try {
            filmService.create(film);
            return new ResponseEntity<>(film, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(film, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity <Film> update(@RequestBody Film film) { // обновление данных по уже внесенному в базу приложения фильму
        try {
            filmService.update(film);
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(film, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Film>> readAll () { // получение списка всех фильмов, ранее внесенных в базу приложения
        try {
            return new ResponseEntity<>(filmService.readAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
