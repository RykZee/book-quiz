package com.example.book_quiz.error;

public class BookValidationException extends RuntimeException {
    public BookValidationException(String message) {
        super(message);
    }

    public BookValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
