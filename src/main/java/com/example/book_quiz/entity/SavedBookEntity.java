package com.example.book_quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "books")
@Data
public class SavedBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "authorkey")
    private List<String> authorKey;
    @Column(name = "authorname")
    private List<String> authorName;
    private String title;
    @Column(name = "firstpublishyear")
    private Integer firstPublishYear;
}
