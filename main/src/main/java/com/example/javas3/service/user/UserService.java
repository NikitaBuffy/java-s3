package com.example.javas3.service.user;

import com.example.javas3.dto.user.UserDto;
import com.example.javas3.model.user.User;

public interface UserService {

    UserDto addUser(UserDto userDto);

    void deleteUser(Long userId);

    User getExistingUser(Long userId);
}
