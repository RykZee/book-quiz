package com.example.book_quiz.api;

import com.example.book_quiz.model.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class Client {
    Map<String, String> queryParams = Map.of("q", "");
    private final HttpClient client;
    private final String baseUrl = "https://openlibrary.org";

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record SearchResponse(Integer numFound, Integer start, Integer offset, List<Book> docs) {}

    public Client() {
        client = HttpClient.newHttpClient();
    }

    public List<Book> searchBooks(String query) throws IOException, InterruptedException {
        String url = baseUrl + "/search.json" + queryParams(query);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            // TODO: Handle errors
            System.err.println("Error");
        }
        SearchResponse searchResponse = new ObjectMapper().readValue(response.body(), SearchResponse.class);
        return searchResponse.docs;
    }

    private String queryParams(String query) {
        return "?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&limit=2";
    }
}
