package com.yousra.store.repository;

import com.yousra.store.model.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void testSaveAndFindById() {
        Article article = new Article();
        article.setName("Chaussure");
        article.setPrice(49.99);
        article.setDescription("Un article de test");

        Article saved = articleRepository.save(article);
        Optional<Article> found = articleRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Chaussure", found.get().getName());
    }
}
