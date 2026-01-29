package com.yousra.store.controller;

import com.yousra.store.dto.ArticleCreateDto;
import com.yousra.store.dto.ArticleDto;
import com.yousra.store.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(articleService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ArticleDto> create(@Valid @RequestBody ArticleCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.create(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        articleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
