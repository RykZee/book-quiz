package com.example.book_quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class AuthTestController {

    @GetMapping("/auth")
    public ResponseEntity<Map<String, String>> testAuth(Authentication authentication) {
        return ResponseEntity.ok(Map.of(
            "message", "You are authenticated!",
            "username", authentication.getName()
        ));
    }
}