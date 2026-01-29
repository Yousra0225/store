package com.yousra.store.repository;

import com.yousra.store.model.LigneArticle;
import com.yousra.store.model.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LigneArticleRepository extends JpaRepository<LigneArticle, UUID> {

    List<LigneArticle> findByPanier(Panier panier);

    Optional<LigneArticle> findByPanierAndArticle_Id(Panier panier, UUID articleId);
}
