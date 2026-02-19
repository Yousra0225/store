package com.yousra.store.controller;

import com.yousra.store.dto.ArticleDto;
import com.yousra.store.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<Void> addToWishlist(Authentication auth, @PathVariable UUID articleId) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        wishlistService.addToWishlist(auth.getName(), articleId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> removeFromWishlist(Authentication auth, @PathVariable UUID articleId) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        wishlistService.removeFromWishlist(auth.getName(), articleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getWishlist(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(wishlistService.getWishlist(auth.getName()));
    }
}
