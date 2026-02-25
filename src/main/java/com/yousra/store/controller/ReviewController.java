package com.yousra.store.controller;

import com.yousra.store.dto.ReviewCreateDto;
import com.yousra.store.dto.ReviewDto;
import com.yousra.store.security.AppUserDetails;
import com.yousra.store.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@AuthenticationPrincipal AppUserDetails user, @RequestBody ReviewCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(user.getId(), dto));
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
