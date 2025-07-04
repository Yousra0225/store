package com.yousra.store.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String url;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
    /**
     * Constructor
     */
    public Image(){}

    /**
     * Getters
     */
    public UUID getId() {return this.id;}
    public String getUrl(){return this.url;}
    public Article getArticle() {return this.article;}
    /**
     * Setters
     */
    public void setId(UUID id){this.id = id;}
    public void setUrl(String url){this.url = url;}
    public void setArticle(Article article) {this.article = article;}

    /**
     * to string method
     * @return string
     */
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
