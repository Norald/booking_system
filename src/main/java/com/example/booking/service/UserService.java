package com.example.booking.service;

import com.example.booking.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    UserDTO createUser(String username, String email, String password);

    UserDTO getUserByEmail(String email);

}
