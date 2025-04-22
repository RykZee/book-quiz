package com.example.book_quiz.service;

import com.example.book_quiz.entity.AuthorEntity;
import com.example.book_quiz.entity.SavedBookEntity;
import com.example.book_quiz.entity.UserEntity;
import com.example.book_quiz.model.Book;
import com.example.book_quiz.model.CustomUserDetails;
import com.example.book_quiz.repository.AuthorRepository;
import com.example.book_quiz.repository.SavedBookRepository;
import com.example.book_quiz.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SavedBookService {
    private final SavedBookRepository savedBookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final Logger logger;

    @Autowired
    public SavedBookService(SavedBookRepository savedBookRepository,
                            AuthorRepository authorRepository,
                            UserRepository userRepository,
                            @Qualifier("savedBookServiceLogger") Logger logger) {
        this.savedBookRepository = savedBookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public Book saveBook(Book book, CustomUserDetails userDetails) {
        logger.debug("Saving book {}", book.title());

        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("User with " + userDetails.getId() + " couldn't be found"));

        SavedBookEntity bookEntityInDatabase = savedBookRepository.findById(book.id()).orElse(null);
        if (bookEntityInDatabase != null) {
            user.getSavedBooks().add(bookEntityInDatabase);
            return convertEntityToModel(bookEntityInDatabase);
        }

        Set<AuthorEntity> authors = book.authors().stream()
                .map(authorRepository::findByFullName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        saveMissingAuthors(authors, book);

        SavedBookEntity savedBookEntity = new SavedBookEntity(
                book.id(),
                book.title(),
                authors,
                book.publishedDate(),
                book.isbn10(),
                book.isbn13()
        );

        for (AuthorEntity authorEntity : authors) {
            authorEntity.getBooks().add(savedBookEntity);
        }

        user.getSavedBooks().add(savedBookEntity);
        return book;
    }

    private void saveMissingAuthors(Set<AuthorEntity> authors, Book book) {
        if (authors.size() != book.authors().size()) {
            Set<String> currentAuthorNames = authors.stream()
                    .map(AuthorEntity::getFullName)
                    .collect(Collectors.toSet());

            List<String> missingAuthors = book.authors().stream()
                    .filter(authorName -> !currentAuthorNames.contains(authorName))
                    .toList();

            for (String missingAuthor : missingAuthors) {
                AuthorEntity authorEntity = new AuthorEntity(null, missingAuthor, new HashSet<>());
                authors.add(authorEntity);
            }
        }
    }

    private Book convertEntityToModel(SavedBookEntity savedBookEntity) {
        List<String> authorsNames = savedBookEntity
                .getAuthors()
                .stream()
                .map(AuthorEntity::getFullName)
                .toList();

        return new Book(
                savedBookEntity.getId(),
                savedBookEntity.getTitle(),
                authorsNames,
                savedBookEntity.getPublishedDate(),
                savedBookEntity.getIsbn10(),
                savedBookEntity.getIsbn13()
        );
    }
}
