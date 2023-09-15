package com.example.javas3.service.user;

import com.example.javas3.dto.user.UserDto;
import com.example.javas3.dto.user.UserMapper;
import com.example.javas3.model.user.User;
import com.example.javas3.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        log.info("Created new user: user = {}", user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = getExistingUser(userId);
        userRepository.delete(user);
        log.info("Deleted user with ID = {}", user.getId());
    }

    @Override
    public User getExistingUser(Long userId) {
        return userRepository.getExistingUser(userId);
    }
}
