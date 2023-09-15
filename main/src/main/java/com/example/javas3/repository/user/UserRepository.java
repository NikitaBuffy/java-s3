package com.example.javas3.repository.user;

import com.example.javas3.exception.UserNotFoundException;
import com.example.javas3.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default User getExistingUser(Long userId) {
        return findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException(String.format("User with id=%d was not found", userId));
        });
    }
}
