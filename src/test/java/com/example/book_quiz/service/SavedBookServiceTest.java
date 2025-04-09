package com.example.book_quiz.service;

import com.example.book_quiz.entity.SavedBookEntity;
import com.example.book_quiz.model.Book;
import com.example.book_quiz.repository.SavedBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SavedBookServiceTest {

    @Autowired
    private SavedBookService savedBookService;
    
    @Autowired
    private SavedBookRepository savedBookRepository;
    
    @BeforeEach
    void setup() {
        savedBookRepository.deleteAll();
    }

    @Test
    void testGetAllSavedBooks() {
        SavedBookEntity book = new SavedBookEntity();
        book.setTitle("Test Book");
        book.setAuthorName(List.of("Test Author"));
        book.setAuthorKey(List.of("OL123456A"));
        book.setFirstPublishYear(2023);
        
        savedBookRepository.save(book);
        List<Book> result = savedBookService.getAllSavedBooks();

        assertThat(result).hasSize(1);
        Book actual = result.getFirst();
        assertThat(actual.title()).isEqualTo("Test Book");
        assertThat(actual.authorName().getFirst()).isEqualTo("Test Author");
        assertThat(actual.authorKey().getFirst()).isEqualTo("OL123456A");
        assertThat(actual.firstPublishYear()).isEqualTo(2023);
    }
}