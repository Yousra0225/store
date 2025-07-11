package com.yousra.store.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column
    private String name;

    @OneToMany(mappedBy = "categorie")
    private List<Article> articles;
    
}
