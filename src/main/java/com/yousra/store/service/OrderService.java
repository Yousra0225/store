package com.yousra.store.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private PaymentService paymentService;
    // @Autowired
    public OrderService(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    public void placeOrder(){
        paymentService.processPayment(9.99);
    }

    public void setPaymentService(PaymentService paymentService){
        this.paymentService = paymentService;
    }
}
