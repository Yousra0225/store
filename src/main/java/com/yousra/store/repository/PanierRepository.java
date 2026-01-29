package com.yousra.store.repository;

import com.yousra.store.model.Panier;
import com.yousra.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PanierRepository extends JpaRepository<Panier, UUID> {

    Optional<Panier> findByUser(User user);

    Optional<Panier> findByUserId(UUID userId);
}
