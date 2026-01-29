package com.yousra.store.dto;

import com.yousra.store.model.Gender;
import jakarta.validation.constraints.*;
public record UserCreateDto(
    @NotBlank @Size(max = 100)
    String firstName,
    @NotBlank @Size(max = 100)
    String lastName,
    @NotBlank @Email @Size(max = 255)
    String email,
    @NotBlank @Size(max = 500)
    String postalAddress,
    @NotBlank @Size(min = 6, max = 100)
    String password,
    Gender gender
) {}
