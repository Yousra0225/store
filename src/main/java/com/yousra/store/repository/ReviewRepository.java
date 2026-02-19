package com.yousra.store.repository;

import com.yousra.store.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByArticleId(UUID articleId);
    boolean existsByUserIdAndArticleId(UUID userId, UUID articleId);
}
