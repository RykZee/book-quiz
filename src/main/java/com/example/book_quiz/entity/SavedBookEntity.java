package com.example.book_quiz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedBookEntity {

    @Id
    private String id;
    private String title;

    @ManyToMany(mappedBy = "books")
    private Set<AuthorEntity> authors = new HashSet<>();
    private String publishedDate;
    private String isbn10;
    private String isbn13;

    public void addAuthor(AuthorEntity authorEntity) {
        authors.add(authorEntity);
        for (SavedBookEntity book : authorEntity.getBooks()) {

        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SavedBookEntity that = (SavedBookEntity) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(publishedDate, that.publishedDate) &&
                Objects.equals(isbn10, that.isbn10) &&
                Objects.equals(isbn13, that.isbn13);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publishedDate, isbn10, isbn13);
    }
}
