package com.example.javas3.dto.user;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Имя пользователя
     */
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}
