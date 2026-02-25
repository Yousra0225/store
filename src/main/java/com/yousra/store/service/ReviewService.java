package com.yousra.store.service;

import com.yousra.store.dto.ReviewCreateDto;
import com.yousra.store.dto.ReviewDto;
import com.yousra.store.exception.ResourceNotFoundException;
import com.yousra.store.model.*;
import com.yousra.store.repository.ArticleRepository;
import com.yousra.store.repository.CommandRepository;
import com.yousra.store.repository.ReviewRepository;
import com.yousra.store.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommandRepository commandRepository;

    public ReviewService(ReviewRepository reviewRepository, ArticleRepository articleRepository, 
                         UserRepository userRepository, CommandRepository commandRepository) {
        this.reviewRepository = reviewRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commandRepository = commandRepository;
    }

    @Transactional
    public ReviewDto createReview(UUID userId, ReviewCreateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + userId));
        
        Article article = articleRepository.findById(dto.articleId())
                .orElseThrow(() -> new ResourceNotFoundException("Article", dto.articleId()));

        // Check if user has already reviewed this article
        if (reviewRepository.existsByUserIdAndArticleId(user.getId(), article.getId())) {
            throw new IllegalArgumentException("Vous avez déjà donné votre avis sur cet article.");
        }

        // Check if user has bought this article
        boolean hasBought = commandRepository.hasUserBoughtArticle(user.getId(), article.getId());
        if (!hasBought) {
            throw new IllegalArgumentException("Vous ne pouvez donner votre avis que sur les articles que vous avez achetés.");
        }

        Review review = new Review();
        review.setRating(dto.rating());
        review.setComment(dto.comment());
        review.setUser(user);
        review.setArticle(article);

        review = reviewRepository.save(review);
        return toDto(review);
    }

    public List<ReviewDto> getReviewsForArticle(UUID articleId) {
        return reviewRepository.findByArticleId(articleId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Double getAverageRating(UUID articleId) {
        List<Review> reviews = reviewRepository.findByArticleId(articleId);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    private ReviewDto toDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getUser().getFirstName(),
                review.getUser().getLastName(),
                review.getCreatedAt()
        );
    }
}
