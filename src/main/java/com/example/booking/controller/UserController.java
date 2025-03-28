package com.example.booking.controller;

import com.example.booking.dto.UserDTO;
import com.example.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(userService.createUser(username, email, password));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
}
