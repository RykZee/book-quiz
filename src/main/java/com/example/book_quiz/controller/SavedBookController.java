package com.example.book_quiz.controller;

import com.example.book_quiz.model.Book;
import com.example.book_quiz.model.CustomUserDetails;
import com.example.book_quiz.service.SavedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saved-book")
public class SavedBookController {
    private final SavedBookService savedBookService;

    @Autowired
    public SavedBookController(SavedBookService savedBookService) {
        this.savedBookService = savedBookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(savedBookService.getAllSavedBooks());
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book, @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(book);
    }
}
