package com.example.book_quiz.repository;

import com.example.book_quiz.entity.SavedBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedBookRepository extends JpaRepository<SavedBookEntity, String> {

}
