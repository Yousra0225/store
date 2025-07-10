package com.yousra.store.model;

import jakarta.persistence.*;

import java.util.UUID;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column (nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String  postalAddress;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender Gender;

    public User(UUID id,String firstName, String lastName, String emailAddress,
                String postalAddress, String, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.postalAddress = postalAddress;
        this.password = password;

    }
}
