package com.example.booking.controller;

import com.example.booking.dto.UserDTO;
import com.example.booking.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldRegisterUser() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "JohnDoe", "john@example.com");

        Mockito.when(userService.createUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(userDTO);

        mockMvc.perform(post("/users/register")
                        .param("username", "JohnDoe")
                        .param("email", "john@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("JohnDoe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void shouldGetUserByEmail() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "JohnDoe", "john@example.com");

        Mockito.when(userService.getUserByEmail("john@example.com")).thenReturn(userDTO);

        mockMvc.perform(get("/users/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}
