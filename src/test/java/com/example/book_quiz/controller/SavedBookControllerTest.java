package com.example.book_quiz.controller;

import com.example.book_quiz.entity.SavedBookEntity;
import com.example.book_quiz.repository.SavedBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SavedBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SavedBookRepository savedBookRepository;

    @BeforeEach
    void setup() {
        savedBookRepository.deleteAll();

        SavedBookEntity book = new SavedBookEntity();
        book.setTitle("Test Book");
        book.setAuthorName(List.of("Test Author"));
        book.setAuthorKey(List.of("OL123456A"));
        book.setFirstPublishYear(2023);
        
        savedBookRepository.save(book);
    }

    @Test
    @WithMockUser
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/saved-book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].author_name[0]").value("Test Author"))
                .andExpect(jsonPath("$[0].author_key[0]").value("OL123456A"))
                .andExpect(jsonPath("$[0].first_publish_year").value(2023));
    }
}