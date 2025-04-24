package com.example.book_quiz.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public void created() {
        final var now = LocalDateTime.now(ZoneOffset.UTC);
        createdAt = now;
        updatedAt = now;
    }

    public void updated() {
        updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}
