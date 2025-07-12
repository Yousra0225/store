package com.ta_boutique.model;

import com.yousra.store.model.Article;
import com.yousra.store.model.Command;
import com.yousra.store.model.Panier;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class LigneArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    private Article article;

    private int quantity;

    private BigDecimal lignePrice;

    @ManyToOne
    private Panier panier;

    @ManyToOne
    private Command command;

    public LigneArticle(int quantity, BigDecimal lignePrice) {
        this.quantity = quantity;
        this.lignePrice = lignePrice;
    }

    /**
     * Getters
     */
    public UUID getId(){return this.id;}
    public Article getArticle(){return this.article;}
    public int getQuantity(){return this.quantity;}
    public BigDecimal getLignePrice(){return this.lignePrice;}
    public Panier getPanier(){return this.panier;}
    public Command getCommand(){return this.command;}

    /**
     * Setters
     */
    public void setId(UUID id){this.id = id;}
    public void setArticle(Article article){this.article = article;}
    public void setQuantity(BigDecimal quantity){this.quantity = quantity;}

}
