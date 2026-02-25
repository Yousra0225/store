package com.yousra.store.integration;

import com.yousra.store.dto.*;
import com.yousra.store.model.OrderStatus;
import com.yousra.store.repository.ArticleRepository;
import com.yousra.store.repository.CommandRepository;
import com.yousra.store.repository.UserRepository;
import com.yousra.store.model.Command;
import com.yousra.store.model.LigneArticle;
import com.yousra.store.model.User;
import com.yousra.store.model.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewAndWishlistApiIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommandRepository commandRepository;

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    private String userEmail;
    private String userPassword = "password123";
    private UUID articleId;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        articleRepository.deleteAll();
        commandRepository.deleteAll();

        userEmail = "user-" + System.currentTimeMillis() + "@test.fr";
        Map<String, Object> registerBody = Map.of(
                "firstName", "Test",
                "lastName", "User",
                "email", userEmail,
                "postalAddress", "1 rue Test",
                "password", userPassword
        );
        restTemplate.postForEntity(baseUrl() + "/api/auth/register", registerBody, Map.class);

        Article article = new Article();
        article.setName("Test Article");
        article.setPrice(100.0);
        article.setStockQuantity(10);
        article.setDescription("Description");
        article = articleRepository.save(article);
        articleId = article.getId();
    }

    @Test
    void wishlist_addAndRemove_works() {
        TestRestTemplate authRest = new TestRestTemplate(userEmail, userPassword);

        // Add to wishlist
        ResponseEntity<Void> addResponse = authRest.postForEntity(baseUrl() + "/api/wishlist/" + articleId, null, Void.class);
        assertThat(addResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Get wishlist
        ResponseEntity<List> getResponse = authRest.getForEntity(baseUrl() + "/api/wishlist", List.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).hasSize(1);

        // Remove from wishlist
        authRest.delete(baseUrl() + "/api/wishlist/" + articleId);

        // Get wishlist again
        getResponse = authRest.getForEntity(baseUrl() + "/api/wishlist", List.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isEmpty();
    }

    @Test
    void review_create_failsIfNotBought() {
        TestRestTemplate authRest = new TestRestTemplate(userEmail, userPassword);

        ReviewCreateDto reviewDto = new ReviewCreateDto(5, "Great product", articleId);
        ResponseEntity<Map> response = authRest.postForEntity(baseUrl() + "/api/reviews", reviewDto, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().get("message")).toString().contains("uniquement sur les articles achetés");
    }

    @Test
    void review_create_worksIfBought() {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Article article = articleRepository.findById(articleId).orElseThrow();

        // Simulate a purchase
        Command command = new Command();
        command.setUser(user);
        command.setPaymentMethodType("CARD");
        command.setTotalAmount(BigDecimal.valueOf(100.0));
        command.setStatus(OrderStatus.PAYEE);
        
        LigneArticle ligne = new LigneArticle();
        ligne.setArticle(article);
        ligne.setQuantity(1);
        ligne.setLignePrice(BigDecimal.valueOf(100.0));
        ligne.setCommand(command);
        command.getLignes().add(ligne);
        
        commandRepository.save(command);

        TestRestTemplate authRest = new TestRestTemplate(userEmail, userPassword);

        ReviewCreateDto reviewDto = new ReviewCreateDto(5, "Great product", articleId);
        ResponseEntity<ReviewDto> response = authRest.postForEntity(baseUrl() + "/api/reviews", reviewDto, ReviewDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().rating()).isEqualTo(5);
        assertThat(response.getBody().comment()).isEqualTo("Great product");

        // Check article average rating
        ResponseEntity<Double> avgResponse = restTemplate.getForEntity(baseUrl() + "/api/reviews/article/" + articleId + "/average-rating", Double.class);
        assertThat(avgResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(avgResponse.getBody()).isEqualTo(5.0);
    }
}
