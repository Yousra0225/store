package com.yousra.store.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthAndPanierApiIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void register_puis_getPanier_avecAuth_retourne200() {
        String email = "test-" + System.currentTimeMillis() + "@store.fr";
        String password = "password123";

        Map<String, Object> body = Map.of(
                "firstName", "Test",
                "lastName", "User",
                "email", email,
                "postalAddress", "1 rue Test",
                "password", password
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Map> registerResponse = restTemplate.postForEntity(
                baseUrl() + "/api/auth/register",
                new HttpEntity<>(body, headers),
                Map.class);

        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        TestRestTemplate authRest = new TestRestTemplate(email, password);
        ResponseEntity<Map> panierResponse = authRest.getForEntity(baseUrl() + "/api/panier", Map.class);

        assertThat(panierResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(panierResponse.getBody()).containsKey("lignes");
        assertThat(panierResponse.getBody()).containsKey("totalPrice");
    }

    @Test
    void register_puis_getUsersMe_avecAuth_retourne200() {
        String email = "me-" + System.currentTimeMillis() + "@store.fr";
        String password = "password123";

        Map<String, Object> body = Map.of(
                "firstName", "Me",
                "lastName", "User",
                "email", email,
                "postalAddress", "2 rue Test",
                "password", password
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.postForEntity(baseUrl() + "/api/auth/register", new HttpEntity<>(body, headers), Map.class);

        TestRestTemplate authRest = new TestRestTemplate(email, password);
        ResponseEntity<Map> meResponse = authRest.getForEntity(baseUrl() + "/api/users/me", Map.class);

        assertThat(meResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(meResponse.getBody()).isNotNull();
        assertThat(meResponse.getBody().get("email")).isEqualTo(email);
    }

    @Test
    void getPanier_sansAuth_retourne401() {
        ResponseEntity<Map> response = restTemplate.getForEntity(baseUrl() + "/api/panier", Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
