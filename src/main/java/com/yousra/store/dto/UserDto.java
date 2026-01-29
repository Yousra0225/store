package com.yousra.store.dto;

import com.yousra.store.model.Gender;
import java.util.UUID;

public record UserDto(
    UUID id,
    String firstName,
    String lastName,
    String email,
    String postalAddress,
    Gender gender
) {}
