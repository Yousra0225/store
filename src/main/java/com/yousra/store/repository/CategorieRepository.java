package com.yousra.store.repository;

import com.yousra.store.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategorieRepository extends JpaRepository<Categorie, UUID> {
}
