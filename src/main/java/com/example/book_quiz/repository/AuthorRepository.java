package com.example.book_quiz.repository;

import com.example.book_quiz.entity.AuthorEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findByFullName(String fullName);
}
