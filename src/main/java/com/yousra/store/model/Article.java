package com.yousra.store.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Article {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    /**
     * Contructeurs de la class
     */
    public Article(){this.images = new ArrayList<>();}

    /**
     * Getters
     */
    public UUID getId(){return this.id;}
    public String getName() { return this.name; }
    public double getPrice(){return this.price;}
    public int getStockQuantity() { return this.stockQuantity; }
    public int getSalesCount() { return this.salesCount; }
    public String getDescription() { return this.description; }
    public List<Image> getImages() { return images; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    /**
     * Setters
     */
    public void setId(UUID id) { this.id = id; }
    public void setName(String Name){this.name=Name;}
    public void setPrice(double price) { this.price = price; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public void setSalesCount(int salesCount) { this.salesCount = salesCount; }
    public void setDescription(String description) { this.description = description; }
    public void setImages(List<Image> images) { this.images = images; }

    /**
     * ToString method
     * @return article
     */
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", salesCount=" + salesCount +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Method to generate hashcode
     * @return hashcode
     */
    public int hashCode() {return Objects.hash(id);}

}
