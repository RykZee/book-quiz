package com.example.book_quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Book(
        String id,
        String title,
        List<String> authors,
        String publishedDate,
        String isbn10,
        String isbn13
) {}