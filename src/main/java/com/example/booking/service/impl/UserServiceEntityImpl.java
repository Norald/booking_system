package com.example.booking.service.impl;

import com.example.booking.model.User;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.UserServiceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceEntityImpl implements UserServiceEntity {

    private final UserRepository userRepository;

    @Override
    public User createUser(String username, String email, String password) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

}
