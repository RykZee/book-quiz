package com.example.book_quiz.repository;

import com.example.book_quiz.entity.AuthorEntity;
import com.example.book_quiz.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SavedBookRepositoryTest {

    @Autowired
    private SavedBookRepository savedBookRepository;

    @Test
    void testSaveAndFindBook() {
        BookEntity book = new BookEntity();
        book.setId("OL123456A");
        book.setTitle("Test Book");
        book.setPublishedDate("2023");

        AuthorEntity author = new AuthorEntity();
        author.setFullName("Test Author");
        author.getBooks().add(book);
        book.getAuthors().add(author);

        BookEntity savedBook = savedBookRepository.save(book);
        assertThat(savedBook.getId()).isNotNull();
        Iterable<BookEntity> books = savedBookRepository.findAll();
        assertThat(books).hasSize(1);
        BookEntity actual = books.iterator().next();
        assertThat(actual.getId()).isEqualTo("OL123456A");
        assertThat(actual.getTitle()).isEqualTo("Test Book");
        assertThat(actual.getPublishedDate()).isEqualTo("2023");

        AuthorEntity actualAuthor = actual.getAuthors().stream().findFirst().orElseThrow(
                () -> new RuntimeException("Expected AuthorEntity but none were present")
        );
        assertThat(actualAuthor.getId()).isNotNull();
        assertThat(actualAuthor.getFullName()).isEqualTo("Test Author");
    }
}