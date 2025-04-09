package com.example.book_quiz.repository;

import com.example.book_quiz.entity.SavedBookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SavedBookRepositoryTest {

    @Autowired
    private SavedBookRepository savedBookRepository;

    @Test
    void testSaveAndFindBook() {
        SavedBookEntity book = new SavedBookEntity();
        book.setTitle("Test Book");
        book.setAuthorName(List.of("Test Author"));
        book.setAuthorKey(List.of("OL123456A"));
        book.setFirstPublishYear(2023);

        SavedBookEntity savedBook = savedBookRepository.save(book);
        assertThat(savedBook.getId()).isNotNull();
        Iterable<SavedBookEntity> books = savedBookRepository.findAll();
        assertThat(books).hasSize(1);
        SavedBookEntity actual = books.iterator().next();
        assertThat(actual.getTitle()).isEqualTo("Test Book");
        assertThat(actual.getAuthorName().getFirst()).isEqualTo("Test Author");
        assertThat(actual.getAuthorKey().getFirst()).isEqualTo("OL123456A");
        assertThat(actual.getFirstPublishYear()).isEqualTo(2023);
    }
}