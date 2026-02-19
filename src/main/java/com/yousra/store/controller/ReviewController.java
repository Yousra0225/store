package com.yousra.store.controller;

import com.yousra.store.dto.ReviewCreateDto;
import com.yousra.store.dto.ReviewDto;
import com.yousra.store.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(Authentication auth, @RequestBody ReviewCreateDto dto) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(auth.getName(), dto));
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<ReviewDto>> getReviewsForArticle(@PathVariable UUID articleId) {
        return ResponseEntity.ok(reviewService.getReviewsForArticle(articleId));
    }

    @GetMapping("/article/{articleId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable UUID articleId) {
        return ResponseEntity.ok(reviewService.getAverageRating(articleId));
    }
}
