package com.yousra.store.dto;

import com.yousra.store.model.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CommandDto(
    UUID id,
    UUID trackingNumber,
    String paymentMethodType,
    BigDecimal totalAmount,
    OrderStatus status,
    List<LigneArticleDto> lignes
) {}
