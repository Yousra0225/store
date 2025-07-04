package com.yousra.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

import java.util.UUID;

public class Image {

    @id
    @GeneratedValue
    private UUID id;

    @Column
    private String url;

    public Image(){}

    

}
