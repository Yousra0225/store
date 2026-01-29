package com.yousra.store.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record AddToCartDto(
    @NotNull
    UUID articleId,
    @Min(1)
    int quantity
) {}
