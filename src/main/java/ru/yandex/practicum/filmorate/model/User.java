package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id; //целочисленный идентификатор
    private String email; //электронная почта
    private String login; //логин пользователя
    private String name; //имя для отображения (никнейм)
    private LocalDate birthday; //дата рождения
    private Set<Integer> friends = new HashSet<>(); //друзья пользователя
}
