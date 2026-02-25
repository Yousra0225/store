package com.yousra.store.controller;

import com.yousra.store.dto.ArticleDto;
import com.yousra.store.security.AppUserDetails;
import com.yousra.store.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<Void> addToWishlist(@AuthenticationPrincipal AppUserDetails user, @PathVariable UUID articleId) {
        wishlistService.addToWishlist(user.getId(), articleId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> removeFromWishlist(@AuthenticationPrincipal AppUserDetails user, @PathVariable UUID articleId) {
        wishlistService.removeFromWishlist(user.getId(), articleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getWishlist(@AuthenticationPrincipal AppUserDetails user) {
        return ResponseEntity.ok(wishlistService.getWishlist(user.getId()));
    }
}
