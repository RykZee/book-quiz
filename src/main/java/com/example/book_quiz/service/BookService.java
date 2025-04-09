package com.example.book_quiz.service;

import com.example.book_quiz.api.Client;
import com.example.book_quiz.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final Client client;

    @Autowired
    public BookService(Client client) {
        this.client = client;
    }

    public List<Book> searchBooks(String query) {
        List<Book> result = new ArrayList<>();
        try {
            result = client.searchBooks(query);
        } catch (Exception e) {
            // TODO: Implement proper logic
            System.err.println(e.getMessage());
            System.err.println("Failure");
        }
        return result;
    }
}