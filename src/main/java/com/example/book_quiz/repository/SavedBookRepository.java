package com.example.book_quiz.repository;

import com.example.book_quiz.entity.SavedBookEntity;
import org.springframework.data.repository.CrudRepository;

public interface SavedBookRepository extends CrudRepository<SavedBookEntity, Integer> {

}
