package com.yousra.store.model;

import jakarta.persistence.Column;

public class Command {
    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;
}
