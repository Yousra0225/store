package com.yousra.store.service;

import com.yousra.store.dto.ArticleCreateDto;
import com.yousra.store.dto.ArticleDto;
import com.yousra.store.exception.ResourceNotFoundException;
import com.yousra.store.model.Article;
import com.yousra.store.model.Categorie;
import com.yousra.store.repository.ArticleRepository;
import com.yousra.store.repository.CategorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CategorieRepository categorieRepository;

    @InjectMocks
    private ArticleService articleService;

    private Article article;
    private UUID articleId;

    @BeforeEach
    void setUp() {
        articleId = UUID.randomUUID();
        article = new Article();
        article.setId(articleId);
        article.setName("Chaussure");
        article.setPrice(49.99);
        article.setStockQuantity(10);
        article.setDescription("Description test");
    }

    @Test
    void findAll_retourneListeArticles() {
        when(articleRepository.findAll()).thenReturn(List.of(article));

        List<ArticleDto> result = articleService.findAll();

        assertEquals(1, result.size());
        assertEquals("Chaussure", result.get(0).name());
        assertEquals(10, result.get(0).stockQuantity());
    }

    @Test
    void findById_articleExistant_retourneDto() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        ArticleDto result = articleService.findById(articleId);

        assertEquals(articleId, result.id());
        assertEquals("Chaussure", result.name());
        assertEquals(BigDecimal.valueOf(49.99), result.price());
    }

    @Test
    void findById_articleInexistant_lanceException() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> articleService.findById(articleId));
    }

    @Test
    void create_sansCategorie_persisteArticle() {
        ArticleCreateDto dto = new ArticleCreateDto(
                "Sac", BigDecimal.valueOf(79.99), 5, "Un sac", null, List.of("http://img1.jpg"));
        when(articleRepository.save(any(Article.class))).thenAnswer(i -> {
            Article a = i.getArgument(0);
            a.setId(articleId);
            return a;
        });

        ArticleDto result = articleService.create(dto);

        assertEquals("Sac", result.name());
        assertEquals(5, result.stockQuantity());
        verify(articleRepository).save(any(Article.class));
    }

    @Test
    void deleteById_articleExistant_supprime() {
        when(articleRepository.existsById(articleId)).thenReturn(true);

        articleService.deleteById(articleId);

        verify(articleRepository).deleteById(articleId);
    }

    @Test
    void deleteById_articleInexistant_lanceException() {
        when(articleRepository.existsById(articleId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> articleService.deleteById(articleId));
    }

    @Test
    void isAvailable_stockSuffisant_retourneTrue() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        assertTrue(articleService.isAvailable(articleId, 5));
    }

    @Test
    void isAvailable_stockInsuffisant_retourneFalse() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        assertFalse(articleService.isAvailable(articleId, 20));
    }

    @Test
    void isAvailable_articleInexistant_retourneFalse() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        assertFalse(articleService.isAvailable(articleId, 1));
    }
}
