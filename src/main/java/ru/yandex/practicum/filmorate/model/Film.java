package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Film {

    private Integer id;

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @Size(min = 1, max = 200, message = "Не корректная длинна строки min 1, max 200")
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @Min(value = 1, message = "Продолжительность не меньше 1")
    private long duration;

    private Set<Long> like = new HashSet<>();
}
