package com.yousra.store.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Article {
    @Id 
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stockQuantity;

    @Column
    private int salesCount =0;

    @Column (nullable = false, length = 150)
    private String description;

    // Modélisation de : Un article a plusieurs images
    @OneToMany(mappedBy = "article")
    private List<Image> images ;

    /**
     * Contructeurs de la class
     */
    public Article(){}

    /**
     * Getters
     */
    public UUID getId(){return this.id;}
    public String getName() { return this.name; }
    public double getPrice(){return this.price;}
    public int getStockQuantity() { return this.stockQuantity; }
    public int getSalesCount() { return this.salesCount; }
    public String getDescription() { return this.description; }
    public List<Image> getImages() {return images;}

    /**
     * Setters
     */
    public void setId(UUID id) { this.id = id; }
    public void setName(String Name){this.name=Name;}
    public void setPrice(double price) { this.price = price; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public void setSalesCount(int salesCount) { this.salesCount = salesCount; }
    public void setDescription(String description) { this.description = description; }
    public void setImages(List<Image> images) {this.images = images;}

}
