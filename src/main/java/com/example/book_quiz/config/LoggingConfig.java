package com.example.book_quiz.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {
    
    @Bean
    public Logger bookServiceLogger() {
        return LoggerFactory.getLogger("com.example.book_quiz.service.BookService");
    }
    
    @Bean
    public Logger savedBookServiceLogger() {
        return LoggerFactory.getLogger("com.example.book_quiz.service.SavedBookService");
    }
    
    @Bean
    public Logger userServiceLogger() {
        return LoggerFactory.getLogger("com.example.book_quiz.service.UserService");
    }
    
    @Bean
    public Logger authControllerLogger() {
        return LoggerFactory.getLogger("com.example.book_quiz.controller.AuthController");
    }
    
    @Bean
    public Logger bookControllerLogger() {
        return LoggerFactory.getLogger("com.example.book_quiz.controller.BookController");
    }
    
    @Bean
    public Logger savedBookControllerLogger() {
        return LoggerFactory.getLogger("com.example.book_quiz.controller.SavedBookController");
    }
}