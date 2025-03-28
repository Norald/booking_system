package com.example.booking.service.impl;

import com.example.booking.dto.UserDTO;
import com.example.booking.model.User;
import com.example.booking.service.UserService;
import com.example.booking.service.UserServiceEntity;
import com.example.booking.transformers.UserToUserDTOTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserServiceEntity userServiceEntity;
    private final UserToUserDTOTransformer userToUserDTOTransformer;

    @Override
    public UserDTO createUser(String username, String email, String password) {
        return userToUserDTOTransformer.transform(userServiceEntity.createUser(username, email, password));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userServiceEntity.getUserByEmail(email).orElseThrow(() -> new RuntimeException("Can`t find user by email"));
        return userToUserDTOTransformer.transform(user);
    }
}
