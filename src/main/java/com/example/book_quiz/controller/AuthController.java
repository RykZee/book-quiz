package com.example.book_quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.book_quiz.model.UserDto;
import com.example.book_quiz.model.UserRegistrationRequest;
import com.example.book_quiz.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserRegistrationRequest request) {
        UserDto userDto = userService.registerUser(request.username(), request.password());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login(Authentication authentication) {
        return ResponseEntity.ok(Map.of(
                "message", "Success",
                "username", authentication.getName()
        ));
    }
}