package com.yousra.store.controller;

import com.yousra.store.model.Categorie;
import com.yousra.store.repository.CategorieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    private final CategorieRepository categorieRepository;

    public CategorieController(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @GetMapping
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> findById(@PathVariable UUID id) {
        return ResponseEntity.of(categorieRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<Categorie> create(@RequestBody Categorie categorie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categorieRepository.save(categorie));
    }
}
