package com.example.booking.service;

import com.example.booking.dto.UserDTO;
import com.example.booking.model.User;
import com.example.booking.service.impl.UserServiceImpl;
import com.example.booking.transformers.UserToUserDTOTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserServiceEntity userServiceEntity;

    @Mock
    private UserToUserDTOTransformer userToUserDTOTransformer;

    @Test
    void shouldCreateUser() {
        String username = "testUser";
        String email = "test@example.com";
        String password = "password123";

        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setEmail(email);

        Mockito.when(userServiceEntity.createUser(username, email, password)).thenReturn(user);
        Mockito.when(userToUserDTOTransformer.transform(user)).thenReturn(userDTO);

        UserDTO result = userService.createUser(username, email, password);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());

        Mockito.verify(userServiceEntity, Mockito.times(1)).createUser(username, email, password);
        Mockito.verify(userToUserDTOTransformer, Mockito.times(1)).transform(user);
    }

    @Test
    void shouldGetUserByEmail_Found() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);

        Mockito.when(userServiceEntity.getUserByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(userToUserDTOTransformer.transform(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());

        Mockito.verify(userServiceEntity, Mockito.times(1)).getUserByEmail(email);
        Mockito.verify(userToUserDTOTransformer, Mockito.times(1)).transform(user);
    }

    @Test
    void shouldThrowException_WhenUserNotFoundByEmail() {
        String email = "test@example.com";

        Mockito.when(userServiceEntity.getUserByEmail(email)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserByEmail(email);
        });

        assertEquals("Can`t find user by email", exception.getMessage());

        Mockito.verify(userServiceEntity, Mockito.times(1)).getUserByEmail(email);
        Mockito.verify(userToUserDTOTransformer, Mockito.never()).transform(Mockito.any());
    }
}
