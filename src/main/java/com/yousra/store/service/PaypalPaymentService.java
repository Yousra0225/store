package com.yousra.store.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Placeholder : pas d'intégration PayPal réelle.
 * StubPaymentService est utilisé par défaut.
 */
@Service
@Profile("paypal") // jamais actif sans profil "paypal"
public class PaypalPaymentService implements PaymentService {
    @Override
    public void processPayment(double amount) {
        // Non implémenté
    }
}
