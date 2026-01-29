package com.yousra.store.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID trackingNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String paymentMethodType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.EN_COURS;

    @OneToMany(mappedBy = "command", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneArticle> lignes = new ArrayList<>();

    @PrePersist
    public void initTracking() {
        if (trackingNumber == null) {
            trackingNumber = UUID.randomUUID();
        }
    }

    public Command() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(UUID trackingNumber) { this.trackingNumber = trackingNumber; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getPaymentMethodType() { return paymentMethodType; }
    public void setPaymentMethodType(String paymentMethodType) { this.paymentMethodType = paymentMethodType; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public List<LigneArticle> getLignes() { return lignes; }
    public void setLignes(List<LigneArticle> lignes) { this.lignes = lignes; }
}
