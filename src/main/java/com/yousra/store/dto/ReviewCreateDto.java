package com.yousra.store.dto;

import java.util.UUID;

public record ReviewCreateDto(
    int rating,
    String comment,
    UUID articleId
) {}
