package com.example.book_quiz.entity;

import com.example.book_quiz.model.Book;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity extends BaseEntity {

    public BookEntity(Book book, Set<AuthorEntity> authors, UserEntity user) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authors = authors;
        this.publishedDate = book.getPublishedDate();
        this.isbn10 = book.getIsbn10();
        this.isbn13 = book.getIsbn13();
        this.createdByUser = user;
        this.createdAt = book.getCreatedAt();
        this.updatedAt = book.getUpdatedAt();
    }

    @Id
    private String id;
    private String title;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private Set<AuthorEntity> authors = new HashSet<>();
    private String publishedDate;
    private String isbn10;
    private String isbn13;

    @ManyToOne
    private UserEntity createdByUser;

    public List<String> getAuthorFullNames() {
        return authors.stream()
                .map(AuthorEntity::getFullName)
                .map(String::new)
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
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
