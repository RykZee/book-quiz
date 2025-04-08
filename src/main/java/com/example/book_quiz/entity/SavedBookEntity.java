package com.example.book_quiz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
public class SavedBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "authorkey")
    private List<String> authorKey;
    @Column(name = "authorname")
    private List<String> authorName;
    private String title;
    @Column(name = "firstpublishyear")
    private Integer firstPublishYear;
}
