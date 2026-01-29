package com.yousra.store.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneArticle> lignes = new ArrayList<>();

    public Panier() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<LigneArticle> getLignes() { return lignes; }
    public void setLignes(List<LigneArticle> lignes) { this.lignes = lignes; }

    public BigDecimal getTotalPrice() {
        return lignes.stream()
                .map(LigneArticle::getLignePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
