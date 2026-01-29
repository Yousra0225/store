package com.yousra.store.dto;

import java.math.BigDecimal;
import java.util.List;

public record PanierDto(
    List<LigneArticleDto> lignes,
    BigDecimal totalPrice
) {}
