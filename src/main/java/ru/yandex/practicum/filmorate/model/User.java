package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class User {
    private Long id;

    @Email(message = "Email не корректен")
    @NotEmpty
    private String email;

    @NotEmpty
    @Pattern(regexp = "^\\S*$", message = "Строка не должна содержать пробел")
    private String login;

    private String name;

    @NotNull
    @PastOrPresent
    private LocalDate birthday;

    @JsonIgnore
    private Set<Long> friendsId; //тесты пройдены
}
