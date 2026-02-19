package com.yousra.store.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewDto(
    UUID id,
    int rating,
    String comment,
    String userFirstName,
    String userLastName,
    LocalDateTime createdAt
) {}
