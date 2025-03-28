package com.example.booking.service;

import com.example.booking.model.User;

import java.util.Optional;

public interface UserServiceEntity {
    User createUser(String username, String email, String password);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Long id);
}
