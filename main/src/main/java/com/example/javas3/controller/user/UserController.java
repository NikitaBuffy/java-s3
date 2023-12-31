package com.example.javas3.controller.user;

import com.example.javas3.dto.user.UserDto;
import com.example.javas3.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    /**
     * Создание нового пользователя
     * @param userDto Данные добавляемого пользователя
     * @return Созданный пользователь
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@Validated @RequestBody UserDto userDto)  {
        return userService.addUser(userDto);
    }

    /**
     * Удаление пользователя по ID
     * @param userId ID пользователя
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
