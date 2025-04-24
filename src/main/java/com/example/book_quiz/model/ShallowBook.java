package com.example.book_quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShallowBook(
        String id,
        String title,
        List<String> authors,
        String publishedDate,
        String isbn10,
        String isbn13
) {
    public ShallowBook(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthors(), book.getPublishedDate(), book.getIsbn10(), book.getIsbn13());
    }
}