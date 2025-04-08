package com.example.book_quiz.service;

import com.example.book_quiz.entity.SavedBookEntity;
import com.example.book_quiz.model.Book;
import com.example.book_quiz.repository.SavedBookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class SavedBookService {
    private final SavedBookRepository savedBookRepository;

    @Autowired
    public SavedBookService(SavedBookRepository savedBookRepository) {
        this.savedBookRepository = savedBookRepository;
    }

    @Transactional
    public List<Book> getAllSavedBooks() {
        List<SavedBookEntity> savedBookEntities = StreamSupport
                .stream(savedBookRepository.findAll().spliterator(), true)
                .toList();

        return savedBookEntities
                .stream()
                .map(this::convertEntityToModel)
                .toList();
    }

    private Book convertEntityToModel(SavedBookEntity savedBookEntity) {
        return new Book(
                savedBookEntity.getAuthorKey(),
                savedBookEntity.getAuthorName(),
                savedBookEntity.getTitle(),
                savedBookEntity.getFirstPublishYear(),
                null
        );
    }
}
