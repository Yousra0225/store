package com.yousra.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Création de commande à partir du panier.
 * paymentMethodType : "CARTE", "VIREMENT", etc. (aucun paiement réel).
 */
public record CreateOrderDto(
    @NotBlank @Size(max = 50)
    String paymentMethodType
) {}
