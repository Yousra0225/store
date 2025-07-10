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

    /**
     * Getters
     * @return constructor properties
     */
    public UUID getId() {return id;}
    public String getFirstName() { return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return this.emailAddress;}
    public String getPostalAddress() {return postalAddress;}

    /**
     * Setters
     * @params constructor properties
     */
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setEmail(String email) {this.emailAddress = email;}
    public void setPostalAddress(String postalAddress) {this.postalAddress = postalAddress;}
    public void setPassword(String password) {this.password = password;}

    /**
     * ToString method
     * @return article
     */
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", first name='" + firstName + '\'' +
                ", last name=" + lastName +
                ", email address=" + emailAddress +
                ", postal address=" + postalAddress  +
                '}';
    }
}
}
