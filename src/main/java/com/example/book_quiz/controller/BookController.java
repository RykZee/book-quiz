package com.example.book_quiz.controller;

import com.example.book_quiz.model.ShallowBook;
import com.example.book_quiz.model.Book;
import com.example.book_quiz.model.CustomUserDetails;
import com.example.book_quiz.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<ShallowBook>> getBooks() {
        List<ShallowBook> allBooks = bookService.getAllSavedBooks()
                .stream()
                .map(ShallowBook::new)
                .toList();

        return ResponseEntity.ok(allBooks);
    }

    @PostMapping
    public ResponseEntity<ShallowBook> saveBook(@RequestBody ShallowBook shallowBook, @AuthenticationPrincipal CustomUserDetails user) {
        final var book = new Book
                .Builder()
                    .id(shallowBook.id())
                    .title(shallowBook.title())
                    .authors(shallowBook.authors())
                    .publishedDate(shallowBook.publishedDate())
                    .isbn10(shallowBook.isbn10())
                    .isbn13(shallowBook.isbn13())
                .build();

        Book savedBook = bookService.saveBook(book, user);
        return ResponseEntity.ok(new ShallowBook(savedBook));
    }
}
