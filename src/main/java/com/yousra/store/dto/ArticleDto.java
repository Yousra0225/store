package com.yousra.store.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ArticleDto(
    UUID id,
    String name,
    BigDecimal price,
    int stockQuantity,
    int salesCount,
    String description,
    UUID categorieId,
    String categorieName,
    List<String> imageUrls
) {}
