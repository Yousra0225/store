package com.yousra.store.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleApiIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void getArticles_sansAuth_retourne200() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl() + "/api/articles", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getArticleById_inexistant_retourne404() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                baseUrl() + "/api/articles/00000000-0000-0000-0000-000000000001", Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getCategories_sansAuth_retourne200() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl() + "/api/categories", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
