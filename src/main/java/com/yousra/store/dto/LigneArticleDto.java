package com.yousra.store.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record LigneArticleDto(
    UUID id,
    UUID articleId,
    String articleName,
    int quantity,
    BigDecimal lignePrice
) {}
