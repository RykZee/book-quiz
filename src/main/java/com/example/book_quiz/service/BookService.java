package com.example.book_quiz.service;

import com.example.book_quiz.entity.AuthorEntity;
import com.example.book_quiz.entity.BookEntity;
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

@Service
public class BookService {
    private final SavedBookRepository savedBookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final Logger logger;

    @Autowired
    public BookService(SavedBookRepository savedBookRepository,
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

        List<BookEntity> savedBookEntities = savedBookRepository.findAll()
                .parallelStream()
                .toList();

        logger.debug("Found {} saved books in the database", savedBookEntities.size());

        return savedBookEntities
                .stream()
                .map(this::convertEntityToModel)
                .toList();
    }

    @Transactional
    public Book saveBook(Book book, CustomUserDetails userDetails) {
        logger.debug("Saving book {}", book.getTitle());

        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("User with " + userDetails.getId() + " couldn't be found"));

        BookEntity bookEntityInDatabase = savedBookRepository.findById(book.getId()).orElse(null);
        if (bookEntityInDatabase != null) {
            user.getSavedBooks().add(bookEntityInDatabase);

            return new Book
                    .Builder()
                        .id(bookEntityInDatabase.getId())
                        .title(bookEntityInDatabase.getTitle())
                        .authors(bookEntityInDatabase.getAuthorFullNames())
                        .publishedDate(bookEntityInDatabase.getPublishedDate())
                        .isbn10(bookEntityInDatabase.getIsbn10())
                        .isbn13(bookEntityInDatabase.getIsbn13())
                        .createdAt(bookEntityInDatabase.getCreatedAt())
                        .updatedAt(bookEntityInDatabase.getUpdatedAt())
                    .build();
        }

        Set<AuthorEntity> authors = book.getAuthors().stream()
                .map(authorRepository::findByFullName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        saveMissingAuthors(authors, book);

        BookEntity bookEntity = new BookEntity(book, authors, user);

        for (AuthorEntity authorEntity : authors) {
            authorEntity.getBooks().add(bookEntity);
            authorEntity.updated();
        }

        user.getSavedBooks().add(bookEntity);
        return book;
    }

    private void saveMissingAuthors(Set<AuthorEntity> authors, Book book) {
        if (authors.size() != book.getAuthors().size()) {
            Set<String> currentAuthorNames = authors.stream()
                    .map(AuthorEntity::getFullName)
                    .collect(Collectors.toSet());

            List<String> missingAuthors = book.getAuthors().stream()
                    .filter(authorName -> !currentAuthorNames.contains(authorName))
                    .toList();

            for (String missingAuthor : missingAuthors) {
                AuthorEntity authorEntity = new AuthorEntity(null, missingAuthor, new HashSet<>());
                authorEntity.created();
                authors.add(authorEntity);
            }
        }
    }

    private Book convertEntityToModel(BookEntity bookEntity) {
        return new Book
                .Builder()
                    .id(bookEntity.getId())
                    .title(bookEntity.getTitle())
                    .authors(bookEntity.getAuthorFullNames())
                    .publishedDate(bookEntity.getPublishedDate())
                    .isbn10(bookEntity.getIsbn10())
                    .isbn13(bookEntity.getIsbn13())
                    .createdAt(bookEntity.getCreatedAt())
                    .updatedAt(bookEntity.getUpdatedAt())
                .build();
    }
}
