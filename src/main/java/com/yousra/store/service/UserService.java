package com.yousra.store.service;

import com.yousra.store.dto.UserCreateDto;
import com.yousra.store.dto.UserDto;
import com.yousra.store.exception.ResourceNotFoundException;
import com.yousra.store.model.Panier;
import com.yousra.store.model.User;
import com.yousra.store.repository.PanierRepository;
import com.yousra.store.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PanierRepository panierRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PanierRepository panierRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.panierRepository = panierRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto register(UserCreateDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Un compte existe déjà avec cet email.");
        }
        User u = new User();
        u.setFirstName(dto.firstName());
        u.setLastName(dto.lastName());
        u.setEmail(dto.email());
        u.setPostalAddress(dto.postalAddress());
        u.setPasswordHash(passwordEncoder.encode(dto.password()));
        u.setGender(dto.gender());
        u = userRepository.save(u);
        Panier p = new Panier();
        p.setUser(u);
        panierRepository.save(p);
        u.setPanier(p);
        return toDto(u);
    }

    public UserDto findById(UUID id) {
        return toDto(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id)));
    }

    public UserDto findByEmail(String email) {
        return toDto(userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Utilisateur avec email " + email)));
    }

    public User getEntityById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));
    }

    public User getEntityByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", email));
    }

    private static UserDto toDto(User u) {
        return new UserDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPostalAddress(), u.getGender());
    }
}
