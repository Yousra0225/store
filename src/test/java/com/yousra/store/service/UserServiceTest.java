package com.yousra.store.service;

import com.yousra.store.dto.UserCreateDto;
import com.yousra.store.dto.UserDto;
import com.yousra.store.exception.ResourceNotFoundException;
import com.yousra.store.model.Gender;
import com.yousra.store.model.Panier;
import com.yousra.store.model.User;
import com.yousra.store.repository.PanierRepository;
import com.yousra.store.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PanierRepository panierRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        user.setFirstName("Jean");
        user.setLastName("Dupont");
        user.setEmail("jean@test.fr");
        user.setPostalAddress("1 rue Test");
        user.setPasswordHash("$2a$10$hash");
    }

    @Test
    void register_emailLibre_creeUserEtPanier() {
        UserCreateDto dto = new UserCreateDto(
                "Marie", "Martin", "marie@test.fr", "2 rue Test", "password123", Gender.F);
        when(userRepository.existsByEmail("marie@test.fr")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User u = i.getArgument(0);
            u.setId(userId);
            return u;
        });
        when(panierRepository.save(any(Panier.class))).thenAnswer(i -> i.getArgument(0));

        UserDto result = userService.register(dto);

        assertEquals("Marie", result.firstName());
        assertEquals("marie@test.fr", result.email());
        verify(userRepository).save(any(User.class));
        verify(panierRepository).save(any(Panier.class));
    }

    @Test
    void register_emailDejaUtilise_lanceException() {
        UserCreateDto dto = new UserCreateDto(
                "Jean", "Dupont", "jean@test.fr", "1 rue Test", "pass", null);
        when(userRepository.existsByEmail("jean@test.fr")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.register(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void findById_userExistant_retourneDto() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto result = userService.findById(userId);

        assertEquals(userId, result.id());
        assertEquals("Jean", result.firstName());
        assertEquals("jean@test.fr", result.email());
    }

    @Test
    void findById_userInexistant_lanceException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(userId));
    }
}
