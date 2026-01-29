package com.yousra.store.controller;

import com.yousra.store.dto.AddToCartDto;
import com.yousra.store.dto.PanierDto;
import com.yousra.store.security.AppUserDetails;
import com.yousra.store.service.PanierService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/panier")
public class PanierController {

    private final PanierService panierService;

    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

    @GetMapping
    public ResponseEntity<PanierDto> getPanier(@AuthenticationPrincipal AppUserDetails user) {
        return ResponseEntity.ok(panierService.getPanier(user.getId()));
    }

    @PostMapping("/lignes")
    public ResponseEntity<PanierDto> addToCart(@AuthenticationPrincipal AppUserDetails user,
                                                @Valid @RequestBody AddToCartDto dto) {
        return ResponseEntity.ok(panierService.addToCart(user.getId(), dto));
    }

    @DeleteMapping("/lignes/{ligneId}")
    public ResponseEntity<Void> removeLine(@AuthenticationPrincipal AppUserDetails user,
                                           @PathVariable UUID ligneId) {
        panierService.removeLine(user.getId(), ligneId);
        return ResponseEntity.noContent().build();
    }
}
