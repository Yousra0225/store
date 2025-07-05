package com.yousra.store.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ArticleTest {

    @Test
    public void testArticleProperties() {
        Article article = new Article();
        article.setId(UUID.randomUUID());
        article.setName("Sac à main");
        article.setPrice(79.99);
        article.setStockQuantity(5);
        article.setSalesCount(2);
        article.setDescription("Sac à main en cuir");

        assertEquals("Sac à main", article.getName());
        assertEquals(79.99, article.getPrice());
        assertEquals(5, article.getStockQuantity());
        assertEquals(2, article.getSalesCount());
        assertEquals("Sac à main en cuir", article.getDescription());
    }

    @Test
    public void testImageListAssignment() {
        Article article = new Article();

        Image image1 = new Image();
        image1.setUrl("img1.jpg");
        image1.setArticle(article);

        Image image2 = new Image();
        image2.setUrl("img2.jpg");
        image2.setArticle(article);

        article.setImages(List.of(image1, image2));

        assertNotNull(article.getImages());
        assertEquals(2, article.getImages().size());
        assertEquals("img1.jpg", article.getImages().get(0).getUrl());
    }
}
