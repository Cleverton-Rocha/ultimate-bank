package com.ultimate.bank.model.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(
        @NotNull
        @Length(min = 11, max = 11, message = "CPF must have 11 characters.")
        @Pattern(regexp = "^[0-9]\\d*$", message = "CPF must have only numbers, without special characters or letters.")
        String CPF,
        @NotNull(message = "Password cannot be null")
        @Size(min = 8, max = 100, message = "Password must have at least 8 characters.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
                 message = "Password must have at least one uppercase letter, one lowercase letter and one number.")
        String password) {
}
