package com.example.book_quiz.model;

import com.example.book_quiz.error.BookValidationException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Getter
public class Book {
    private final String id;
    private final String title;
    private final List<String> authors;
    private final String publishedDate;
    private final String isbn10;
    private final String isbn13;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Book(String id, String title, List<String> authors,
                 String publishedDate, String isbn10, String isbn13,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static class Builder {
        private String id;
        private String title;
        private List<String> authors;
        private String publishedDate;
        private String isbn10;
        private String isbn13;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(String id) {
            if (id == null) {
                throw new BookValidationException("Book ID cannot be missing");
            }
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            if (title == null || title.isBlank()) {
                throw new BookValidationException("Book must contain a title");
            }
            this.title = title;
            return this;
        }

        public Builder authors(List<String> authors) {
            this.authors = authors
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(name -> !name.isBlank())
                    .toList();
            return this;
        }

        public Builder publishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder isbn10(String isbn10) {
            this.isbn10 = isbn10;
            return this;
        }

        public Builder isbn13(String isbn13) {
            this.isbn13 = isbn13;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            if (createdAt == null) {
                throw new BookValidationException("Cannot explicitly set createdAt to null");
            }
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            if (updatedAt == null) {
                throw new BookValidationException("Cannot explicitly set updatedAt to null");
            }
            this.updatedAt = updatedAt;
            return this;
        }

        public Book build() {
            if (createdAt == null) {
                final var now = LocalDateTime.now(ZoneOffset.UTC);
                createdAt = now;
                updatedAt = now;
            } else if (updatedAt == null) {
                updatedAt = createdAt;
            }
            return new Book(id, title, authors, publishedDate, isbn10, isbn13, createdAt, updatedAt);
        }
    }
}
