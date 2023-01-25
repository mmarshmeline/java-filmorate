package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

@SpringBootTest
class FilmorateValidatorTests {
    @Autowired
    private UserService userService;
    @Autowired
    private FilmService filmService;

    @Test
    void validateFilmMustThrowExceptionWhenNameIsNull() { //проверка условия 1 - пустое название фильма
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                filmService.getInMemoryFilmStorage().create(Film.builder()
                        .id(1)
                        .name("")
                        .description("В тюрьме для смертников появляется заключенный с божественным даром. " +
                                "Мистическая драма по роману Стивена Кинга")
                        .releaseDate(LocalDate.of(1999, 12, 6))
                        .duration(189)
                        .build()));
        Assertions.assertEquals("Название фильма не может быть пустым", exception.getMessage());
    }

    @Test
    void validateFilmMustThrowExceptionWhenDescriptionLengthIsTooLarge() { //проверка условия 2 - слишком длинное описание
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                filmService.getInMemoryFilmStorage().create(Film.builder()
                        .id(1)
                        .name("Властелин колец: возвращение короля")
                        .description("Повелитель сил тьмы Саурон направляет свою бесчисленную армию под стены " +
                                "Минас-Тирита, крепости Последней Надежды. Он предвкушает близкую победу, " +
                                "но именно это мешает ему заметить две крохотные фигурки — хоббитов, приближающихся " +
                                "к Роковой Горе, где им предстоит уничтожить Кольцо Всевластья.")
                        .releaseDate(LocalDate.of(2003, 12, 1))
                        .duration(201)
                        .build()));
        Assertions.assertEquals("Максимальная длина описания фильма - 200 символов", exception.getMessage());
    }

    @Test
    void validateFilmMustThrowExceptionWhenReleaseDateIsTooEarly() { //проверка условия 3 - дата релиза раньше установленной в программе
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                filmService.getInMemoryFilmStorage().create(Film.builder()
                        .id(1)
                        .name("Сцены в саду Раундхэй")
                        .description("Самый первый фильм в мире")
                        .releaseDate(LocalDate.of(1888, 10, 14))
                        .duration(1)
                        .build()));
        Assertions.assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года.", exception.getMessage());
    }

    @Test
    void validateFilmMustThrowExceptionWhenDurationIsNegative() { //проверка условия 4 - отрицательная продолжительность фильма
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                filmService.getInMemoryFilmStorage().create(Film.builder()
                        .id(1)
                        .name("Форрест Гамп")
                        .description("Полувековая история США глазами чудака из Алабамы. " +
                                "Абсолютная классика Роберта Земекиса с Томом Хэнксом")
                        .releaseDate(LocalDate.of(1994, 6, 23))
                        .duration(-142)
                        .build()));
        Assertions.assertEquals("Продолжительность фильма должна быть положительной.", exception.getMessage());
    }

    @Test
    void validateFilmShouldBeCompletedSuccessfullyWhenFilmIsOk() { //проверка валидации, если объект Film соответствует всем условиям
        Film expectedFilm = Film.builder()
                .id(1)
                .name("Еще один фильм")
                .description("С добавлением этого фильма должно быть все ок.")
                .releaseDate(LocalDate.of(2022, 10, 3))
                .duration(211)
                .build();
        Assertions.assertEquals(expectedFilm, filmService.getInMemoryFilmStorage().create(Film.builder()
                .id(1)
                .name("Еще один фильм")
                .description("С добавлением этого фильма должно быть все ок.")
                .releaseDate(LocalDate.of(2022, 10, 3))
                .duration(211)
                .build()));
    }

    @Test
    void validateUserMustThrowExceptionWhenEmailIsNull() {//проверка условия 1.1 - пустой email
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                userService.getInMemoryUserStorage().create(User.builder()
                        .id(1)
                        .email("")
                        .login("vasiliy1424")
                        .name("Вася Пупкин")
                        .birthday(LocalDate.of(1996, 11, 15))
                        .build()));
        Assertions.assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", exception.getMessage());
    }

    @Test
    void validateUserMustThrowExceptionWhenEmailWithoutAt() { //проверка условия 1.2 - email не содержит @
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                userService.getInMemoryUserStorage().create(User.builder()
                        .id(1)
                        .email("example.ru")
                        .login("petrivanovich1980")
                        .name("Петр")
                        .birthday(LocalDate.of(1980, 12, 15))
                        .build()));
        Assertions.assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", exception.getMessage());
    }

    @Test
    void validateUserMustThrowExceptionWhenLoginIsNull() { //проверка условия 3 - пустой логин
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                userService.getInMemoryUserStorage().create(User.builder()
                        .id(1)
                        .email("elena.abramova93@example.ru")
                        .login("")
                        .name("Елена")
                        .birthday(LocalDate.of(1993, 4, 5))
                        .build()));
        Assertions.assertEquals("Логин не может быть пустым и не может содержать пробелы.", exception.getMessage());
    }

    @Test
    void validateUserMustThrowExceptionWhenBirthdayIsInTheFuture() { //проверка условия 4 - дата рождения в будущем
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                userService.getInMemoryUserStorage().create(User.builder()
                        .id(1)
                        .email("sweetykitty@example.ru")
                        .login("angela_wow")
                        .name("Анжелика")
                        .birthday(LocalDate.of(4998, 5, 13))
                        .build()));
        Assertions.assertEquals("Дата рождения не может быть в будущем.", exception.getMessage());
    }

    @Test
    void validateUserShouldReplaceNameForLoginWhenNameIsNull() { //проверка условия 5 - если имя пустое, вместо него устанавливается логин
        ResponseEntity<User> user = userService.getInMemoryUserStorage().create(User.builder()
                .id(20)
                .email("bednyi@deneg.net")
                .login("thunder08")
                .name("")
                .birthday(LocalDate.of(2001, 3, 19))
                .build());
        Assertions.assertEquals(userService.getInMemoryUserStorage().getUsers().get(20).getName(), userService.getInMemoryUserStorage().getUsers().get(20).getLogin());
    }

    @Test
    void validateUserShouldBeCompletedSuccessfullyWhenUserIsOk() { //проверка валидации если объект User соответствует всем условиям
        User expectedUser = User.builder()
                .id(1)
                .email("lightning@example.ru")
                .login("lightning")
                .name("Антон")
                .birthday(LocalDate.of(1989, 8, 11))
                .build();
        Assertions.assertEquals(expectedUser, userService.getInMemoryUserStorage().create(User.builder()
                .id(345)
                .email("lightning@example.ru")
                .login("lightning")
                .name("Антон")
                .birthday(LocalDate.of(1989, 8, 11))
                .build()));
    }

}
