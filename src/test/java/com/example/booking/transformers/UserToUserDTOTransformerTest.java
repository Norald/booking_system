package com.example.booking.transformers;

import com.example.booking.dto.UserDTO;
import com.example.booking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserToUserDTOTransformerTest {

    private UserToUserDTOTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new UserToUserDTOTransformer();
    }

    @Test
    void shouldTransformUserToDTO() {
        User user = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        UserDTO result = transformer.transform(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
    }
}
