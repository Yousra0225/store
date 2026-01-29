package com.yousra.store.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class LigneArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal lignePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "panier_id")
    private Panier panier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "command_id")
    private Command command;

    public LigneArticle() {}

    public LigneArticle(int quantity, BigDecimal lignePrice) {
        this.quantity = quantity;
        this.lignePrice = lignePrice;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Article getArticle() { return article; }
    public void setArticle(Article article) { this.article = article; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getLignePrice() { return lignePrice; }
    public void setLignePrice(BigDecimal lignePrice) { this.lignePrice = lignePrice; }
    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }
    public Command getCommand() { return command; }
    public void setCommand(Command command) { this.command = command; }
}
