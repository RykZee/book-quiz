package com.example.book_quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Book(
        @JsonProperty("author_key")
        List<String> authorKey,
        @JsonProperty("author_name")
        List<String> authorName,
        String title,
        Integer first_publish_year,
        List<String> isbn
) {}