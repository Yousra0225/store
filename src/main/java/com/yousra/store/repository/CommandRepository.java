package com.yousra.store.repository;

import com.yousra.store.model.Command;
import com.yousra.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommandRepository extends JpaRepository<Command, UUID> {

    List<Command> findByUserOrderByIdDesc(User user);

    @Query("SELECT COUNT(c) > 0 FROM Command c JOIN c.lignes l WHERE c.user.id = :userId AND l.article.id = :articleId AND (c.status = 'PAYEE' OR c.status = 'EXPEDIEE' OR c.status = 'LIVREE')")
    boolean hasUserBoughtArticle(@Param("userId") UUID userId, @Param("articleId") UUID articleId);
}
