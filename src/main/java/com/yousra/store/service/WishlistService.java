package com.yousra.store.service;

import com.yousra.store.dto.ArticleDto;
import com.yousra.store.exception.ResourceNotFoundException;
import com.yousra.store.model.Article;
import com.yousra.store.model.User;
import com.yousra.store.repository.ArticleRepository;
import com.yousra.store.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    public WishlistService(UserRepository userRepository, ArticleRepository articleRepository, ArticleService articleService) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }

    @Transactional
    public void addToWishlist(String userEmail, UUID articleId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + userEmail));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", articleId));
        
        user.getWishlist().add(article);
        userRepository.save(user);
    }

    @Transactional
    public void removeFromWishlist(String userEmail, UUID articleId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + userEmail));
        
        user.getWishlist().removeIf(a -> a.getId().equals(articleId));
        userRepository.save(user);
    }

    public List<ArticleDto> getWishlist(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + userEmail));
        
        return user.getWishlist().stream()
                .map(a -> articleService.findById(a.getId()))
                .collect(Collectors.toList());
    }
}
