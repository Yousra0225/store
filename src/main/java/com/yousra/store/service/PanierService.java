package com.yousra.store.service;

import com.yousra.store.dto.AddToCartDto;
import com.yousra.store.dto.LigneArticleDto;
import com.yousra.store.dto.PanierDto;
import com.yousra.store.exception.ResourceNotFoundException;
import com.yousra.store.model.*;
import com.yousra.store.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PanierService {

    private final PanierRepository panierRepository;
    private final ArticleRepository articleRepository;
    private final LigneArticleRepository ligneArticleRepository;

    public PanierService(PanierRepository panierRepository, ArticleRepository articleRepository,
                         LigneArticleRepository ligneArticleRepository) {
        this.panierRepository = panierRepository;
        this.articleRepository = articleRepository;
        this.ligneArticleRepository = ligneArticleRepository;
    }

    public PanierDto getPanier(UUID userId) {
        Panier panier = panierRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Panier pour l'utilisateur " + userId));
        return toDto(panier);
    }

    @Transactional
    public PanierDto addToCart(UUID userId, AddToCartDto dto) {
        Panier panier = panierRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Panier"));
        Article article = articleRepository.findById(dto.articleId())
                .orElseThrow(() -> new ResourceNotFoundException("Article", dto.articleId()));
        if (article.getStockQuantity() < dto.quantity()) {
            throw new IllegalArgumentException("Stock insuffisant pour cet article.");
        }
        var existing = ligneArticleRepository.findByPanierAndArticle_Id(panier, dto.articleId());
        if (existing.isPresent()) {
            LigneArticle ligne = existing.get();
            ligne.setQuantity(ligne.getQuantity() + dto.quantity());
            ligne.setLignePrice(article.getPrice() != 0 ? BigDecimal.valueOf(article.getPrice()).multiply(BigDecimal.valueOf(ligne.getQuantity())) : BigDecimal.ZERO);
            ligneArticleRepository.save(ligne);
        } else {
            BigDecimal unitPrice = BigDecimal.valueOf(article.getPrice());
            BigDecimal lignePrice = unitPrice.multiply(BigDecimal.valueOf(dto.quantity()));
            LigneArticle ligne = new LigneArticle(dto.quantity(), lignePrice);
            ligne.setArticle(article);
            ligne.setPanier(panier);
            panier.getLignes().add(ligne);
            ligneArticleRepository.save(ligne);
        }
        panierRepository.flush();
        return toDto(panierRepository.findById(panier.getId()).orElse(panier));
    }

    @Transactional
    public void removeLine(UUID userId, UUID ligneId) {
        Panier panier = panierRepository.findByUser_Id(userId).orElseThrow(() -> new ResourceNotFoundException("Panier"));
        LigneArticle ligne = ligneArticleRepository.findById(ligneId).orElseThrow(() -> new ResourceNotFoundException("LigneArticle", ligneId));
        if (!ligne.getPanier().getId().equals(panier.getId())) {
            throw new IllegalArgumentException("Cette ligne n'appartient pas à votre panier.");
        }
        panier.getLignes().remove(ligne);
        ligneArticleRepository.delete(ligne);
    }

    private PanierDto toDto(Panier p) {
        List<LigneArticleDto> lignes = p.getLignes().stream()
                .map(l -> new LigneArticleDto(
                        l.getId(),
                        l.getArticle().getId(),
                        l.getArticle().getName(),
                        l.getQuantity(),
                        l.getLignePrice()))
                .toList();
        return new PanierDto(lignes, p.getTotalPrice());
    }
}
