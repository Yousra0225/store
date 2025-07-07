package com.yousra.store.repository;

import com.yousra.store.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
