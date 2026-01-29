package com.yousra.store.service;

import com.yousra.store.dto.CommandDto;
import com.yousra.store.dto.CreateOrderDto;
import com.yousra.store.dto.LigneArticleDto;
import com.yousra.store.exception.ResourceNotFoundException;
import com.yousra.store.model.*;
import com.yousra.store.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CommandService {

    private final CommandRepository commandRepository;
    private final PanierRepository panierRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public CommandService(CommandRepository commandRepository, PanierRepository panierRepository,
                          UserRepository userRepository, PaymentService paymentService) {
        this.commandRepository = commandRepository;
        this.panierRepository = panierRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }

    @Transactional
    public CommandDto createFromCart(UUID userId, CreateOrderDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", userId));
        Panier panier = panierRepository.findByUser_Id(userId).orElseThrow(() -> new ResourceNotFoundException("Panier"));
        if (panier.getLignes().isEmpty()) {
            throw new IllegalArgumentException("Le panier est vide.");
        }
        for (LigneArticle ligne : panier.getLignes()) {
            if (ligne.getArticle().getStockQuantity() < ligne.getQuantity()) {
                throw new IllegalArgumentException("Stock insuffisant pour l'article : " + ligne.getArticle().getName());
            }
        }
        Command cmd = new Command();
        cmd.setUser(user);
        cmd.setPaymentMethodType(dto.paymentMethodType());
        cmd.setTotalAmount(panier.getTotalPrice());
        cmd.setStatus(OrderStatus.PAYEE);
        commandRepository.save(cmd);
        for (LigneArticle ligne : panier.getLignes()) {
            ligne.setPanier(null);
            ligne.setCommand(cmd);
            cmd.getLignes().add(ligne);
            Article a = ligne.getArticle();
            a.setStockQuantity(a.getStockQuantity() - ligne.getQuantity());
            a.setSalesCount(a.getSalesCount() + ligne.getQuantity());
        }
        panier.getLignes().clear();
        commandRepository.flush();
        paymentService.processPayment(cmd.getTotalAmount().doubleValue());
        return toDto(commandRepository.findById(cmd.getId()).orElse(cmd));
    }

    public List<CommandDto> findByUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", userId));
        return commandRepository.findByUserOrderByIdDesc(user).stream().map(this::toDto).toList();
    }

    public CommandDto findById(UUID id, UUID userId) {
        Command cmd = commandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commande", id));
        if (!cmd.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Commande", id);
        }
        return toDto(cmd);
    }

    private CommandDto toDto(Command c) {
        List<LigneArticleDto> lignes = c.getLignes().stream()
                .map(l -> new LigneArticleDto(
                        l.getId(),
                        l.getArticle().getId(),
                        l.getArticle().getName(),
                        l.getQuantity(),
                        l.getLignePrice()))
                .toList();
        return new CommandDto(c.getId(), c.getTrackingNumber(), c.getPaymentMethodType(), c.getTotalAmount(), c.getStatus(), lignes);
    }
}
