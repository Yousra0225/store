package com.yousra.store.service;

import com.yousra.store.dto.ArticleCreateDto;
import com.yousra.store.dto.ArticleDto;
import com.yousra.store.exception.ResourceNotFoundException;
import com.yousra.store.model.Article;
import com.yousra.store.model.Categorie;
import com.yousra.store.model.Image;
import com.yousra.store.model.Review;
import com.yousra.store.repository.ArticleRepository;
import com.yousra.store.repository.CategorieRepository;
import com.yousra.store.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategorieRepository categorieRepository;
    private final ReviewRepository reviewRepository;

    public ArticleService(ArticleRepository articleRepository, CategorieRepository categorieRepository, ReviewRepository reviewRepository) {
        this.articleRepository = articleRepository;
        this.categorieRepository = categorieRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream().map(this::toDto).toList();
    }

    public ArticleDto findById(UUID id) {
        return toDto(articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article", id)));
    }

    @Transactional
    public ArticleDto create(ArticleCreateDto dto) {
        Article a = new Article();
        a.setName(dto.name());
        a.setPrice(dto.price().doubleValue());
        a.setStockQuantity(dto.stockQuantity());
        a.setDescription(dto.description());
        if (dto.categorieId() != null) {
            Categorie c = categorieRepository.findById(dto.categorieId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categorie", dto.categorieId()));
            a.setCategorie(c);
        }
        if (dto.imageUrls() != null) {
            for (String url : dto.imageUrls()) {
                Image img = new Image();
                img.setUrl(url);
                img.setArticle(a);
                a.getImages().add(img);
            }
        }
        a = articleRepository.save(a);
        return toDto(a);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!articleRepository.existsById(id)) throw new ResourceNotFoundException("Article", id);
        articleRepository.deleteById(id);
    }

    public boolean isAvailable(UUID articleId, int quantity) {
        return articleRepository.findById(articleId)
                .map(a -> a.getStockQuantity() >= quantity)
                .orElse(false);
    }

    private ArticleDto toDto(Article a) {
        List<Review> reviews = reviewRepository.findByArticleId(a.getId());
        Double avgRating = reviews.isEmpty() ? 0.0 : reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        return new ArticleDto(
                a.getId(),
                a.getName(),
                BigDecimal.valueOf(a.getPrice()),
                a.getStockQuantity(),
                a.getSalesCount(),
                a.getDescription(),
                a.getCategorie() != null ? a.getCategorie().getId() : null,
                a.getCategorie() != null ? a.getCategorie().getName() : null,
                a.getImages().stream().map(Image::getUrl).toList(),
                avgRating
        );
    }
}
