package com.yousra.store.controller;

import com.yousra.store.dto.CommandDto;
import com.yousra.store.dto.CreateOrderDto;
import com.yousra.store.security.AppUserDetails;
import com.yousra.store.service.CommandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/commandes")
public class CommandController {

    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<CommandDto> createFromCart(@AuthenticationPrincipal AppUserDetails user,
                                                     @Valid @RequestBody CreateOrderDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.createFromCart(user.getId(), dto));
    }

    @GetMapping
    public List<CommandDto> findByUser(@AuthenticationPrincipal AppUserDetails user) {
        return commandService.findByUser(user.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandDto> findById(@AuthenticationPrincipal AppUserDetails user,
                                                @PathVariable UUID id) {
        return ResponseEntity.ok(commandService.findById(id, user.getId()));
    }
}
