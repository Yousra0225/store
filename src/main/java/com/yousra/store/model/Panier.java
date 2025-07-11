package com.yousra.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
}
