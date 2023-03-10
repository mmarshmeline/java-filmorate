package ru.yandex.practicum.filmorate.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.EntityHasIncorrectFieldsException;
import ru.yandex.practicum.filmorate.exceptions.EntityNotExistException;

@RestControllerAdvice(assignableTypes = {FilmController.class, UserController.class})
@Slf4j
public class MainHandler {

    @ExceptionHandler
    public ResponseEntity<?> throwable(final Throwable e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse("error", e.getMessage()), HttpStatus.valueOf(404));
    }

    @ExceptionHandler(EntityNotExistException.class)
    public ResponseEntity<?> objectNotFound(final EntityNotExistException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse("error", e.getMessage()), HttpStatus.valueOf(404));
    }

    @ExceptionHandler(EntityHasIncorrectFieldsException.class)
    public ResponseEntity<?> validationError(final EntityHasIncorrectFieldsException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse("error", e.getMessage()), HttpStatus.valueOf(400));
    }
}
