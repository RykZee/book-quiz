package com.example.book_quiz.service;

import com.example.book_quiz.entity.SavedBookEntity;
import com.example.book_quiz.model.Book;
import com.example.book_quiz.repository.SavedBookRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class SavedBookService {
    private final SavedBookRepository savedBookRepository;
    private final Logger logger;

    @Autowired
    public SavedBookService(SavedBookRepository savedBookRepository, 
                           @Qualifier("savedBookServiceLogger") Logger logger) {
        this.savedBookRepository = savedBookRepository;
        this.logger = logger;
    }

    @Transactional
    public List<Book> getAllSavedBooks() {
        logger.debug("Fetching all saved books");
        
        List<SavedBookEntity> savedBookEntities = StreamSupport
                .stream(savedBookRepository.findAll().spliterator(), true)
                .toList();
        
        logger.debug("Found {} saved books in the database", savedBookEntities.size());

        List<Book> books = savedBookEntities
                .stream()
                .map(this::convertEntityToModel)
                .toList();
                
        logger.debug("Converted all entity objects to model objects");
        return books;
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
