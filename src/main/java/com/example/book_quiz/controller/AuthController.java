package com.example.book_quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_quiz.model.UserDto;
import com.example.book_quiz.model.UserRegistrationRequest;
import com.example.book_quiz.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserRegistrationRequest request) {
        UserDto userDto = userService.registerUser(request.username(), request.password());
        return ResponseEntity.ok(userDto);
    }
}