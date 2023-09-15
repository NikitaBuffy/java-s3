package com.example.javas3.dto.user;

import com.example.javas3.model.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toUser(UserDto userDto) {
        return new User(
                null,
                userDto.getName()
        );
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName()
        );
    }
}
