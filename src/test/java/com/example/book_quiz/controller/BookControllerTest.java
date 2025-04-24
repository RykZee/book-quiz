package com.example.book_quiz.controller;

import com.example.book_quiz.entity.AuthorEntity;
import com.example.book_quiz.entity.BookEntity;
import com.example.book_quiz.repository.AuthorRepository;
import com.example.book_quiz.repository.SavedBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SavedBookRepository savedBookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setup() {
        savedBookRepository.deleteAll();
        authorRepository.deleteAll();

        BookEntity book = new BookEntity();
        book.setId("OL123456A");
        book.setTitle("Test Book");
        book.setPublishedDate("2023");

        AuthorEntity author = new AuthorEntity();
        author.setFullName("Test Author");

        author.getBooks().add(book);
        book.getAuthors().add(author);

        authorRepository.save(author);
    }

    @Test
    @WithMockUser
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].authors[0]").value("Test Author"))
                .andExpect(jsonPath("$[0].id").value("OL123456A"))
                .andExpect(jsonPath("$[0].publishedDate").value("2023"));
    }
}