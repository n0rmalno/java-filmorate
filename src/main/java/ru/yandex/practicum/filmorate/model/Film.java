package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
public class Film extends BaseUnit {

    @NotBlank
    private String name;

    @Size(min = 1, max = 200, message = "Не корректная длинна строки min 1, max 200")
    private String description;

    private LocalDate releaseDate;

    @Min(value = 1, message = "Продолжительность не меньше 1")
    private int duration;
}
