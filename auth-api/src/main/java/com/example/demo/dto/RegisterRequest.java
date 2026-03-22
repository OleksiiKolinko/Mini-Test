package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RegisterRequest(@NotBlank
                              @Email
                              String email,
                              @Pattern(regexp = "^[0-9a-zA-Z]+$")
                              @NotBlank
                              @Length(min = 8, max = 30)
                              String password) {
}
