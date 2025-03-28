package com.example.booking.service;

import com.example.booking.model.User;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.impl.UserServiceEntityImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceEntityTest {

    @InjectMocks
    private UserServiceEntityImpl userServiceEntity;

    @Mock
    private UserRepository userRepository;

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

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User createdUser = userServiceEntity.createUser(username, email, password);

        assertNotNull(createdUser);
        assertEquals(username, createdUser.getUsername());
        assertEquals(email, createdUser.getEmail());
        assertEquals(password, createdUser.getPassword());

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    void shouldGetUserByEmail_Found() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userServiceEntity.getUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }

    @Test
    void shouldGetUserByEmail_NotFound() {
        String email = "test@example.com";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userServiceEntity.getUserByEmail(email);

        assertTrue(result.isEmpty());

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }

    @Test
    void shouldGetUserById_Found() {
        Long id = 1L;
        User user = new User();
        user.setId(id);

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userServiceEntity.getUserById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void shouldGetUserById_NotFound() {
        Long id = 1L;

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<User> result = userServiceEntity.getUserById(id);

        assertTrue(result.isEmpty());

        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
    }
}
