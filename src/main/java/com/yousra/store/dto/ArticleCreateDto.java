package com.yousra.store.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ArticleCreateDto(
    @NotBlank @Size(max = 50)
    String name,
    @NotNull @DecimalMin("0")
    BigDecimal price,
    @Min(0)
    int stockQuantity,
    @NotBlank @Size(max = 150)
    String description,
    UUID categorieId,
    List<@NotBlank @Size(max = 255) String> imageUrls
) {}
