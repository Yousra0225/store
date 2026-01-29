package com.yousra.store.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Implémentation factice du paiement : aucune intégration réelle (Stripe, PayPal, etc.).
 * Utilisée pour les démonstrations et tests.
 */
@Service
@Primary
public class StubPaymentService implements PaymentService {

    @Override
    public void processPayment(double amount) {
        // Aucun appel externe : simulation acceptée
    }
}
