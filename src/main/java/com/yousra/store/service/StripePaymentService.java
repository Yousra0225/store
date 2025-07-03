package com.yousra.store.service;

public class StripePaymentService implements PaymentService {
    @Override
    public void processPayment(double amount){
        System.out.println("STRIPE");
        System.out.println("AMOUNT: " + amount + " euro");
    }
}
