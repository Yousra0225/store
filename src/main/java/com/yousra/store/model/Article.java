package com.yousra.store.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Article {
    @id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stockQuantity;

    @Column
    private int salesCount;

    @Column (nullable = false, length = 150)
    private String description;
    // Modélisation de : Un article a plusieurs images
    @OneToMany(mappedBy = "article")
    private List<Image> images ;

    /**
     * Contructeur de la class
     */
    public Article(){}

    /**
     * Getters
     */
    public UUID getId(){return this.id}
    public String getFirstnameName() { return this.firstname; }
    public String getLastnameName() { return this.lastname; }
    public double getPrice(){return this.price;}
    public int getStockQuantity() { return this.stockQuantity; }
    public int getSalesCount() { return this.salesCount; }
    public String getDescription() { return this.description; }

    

}
