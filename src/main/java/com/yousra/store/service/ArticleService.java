package com.yousra.store.service;

import com.yousra.store.dto.ArticleCreateDto;
import com.yousra.store.dto.ArticleDto;
import com.yousra.store.exception.ResourceNotFoundException;
import com.yousra.store.model.Article;
import com.yousra.store.model.Categorie;
import com.yousra.store.model.Image;
import com.yousra.store.repository.ArticleRepository;
import com.yousra.store.repository.CategorieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategorieRepository categorieRepository;

    public ArticleService(ArticleRepository articleRepository, CategorieRepository categorieRepository) {
        this.articleRepository = articleRepository;
        this.categorieRepository = categorieRepository;
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
        return new ArticleDto(
                a.getId(),
                a.getName(),
                BigDecimal.valueOf(a.getPrice()),
                a.getStockQuantity(),
                a.getSalesCount(),
                a.getDescription(),
                a.getCategorie() != null ? a.getCategorie().getId() : null,
                a.getCategorie() != null ? a.getCategorie().getName() : null,
                a.getImages().stream().map(Image::getUrl).toList()
        );
    }
}
