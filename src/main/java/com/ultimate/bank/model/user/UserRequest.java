package com.ultimate.bank.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserRequest(
        @NotNull
        @Length(min = 11, max = 11, message = "CPF must have 11 characters")
        String CPF,
        @NotNull(message = "Name cannot be null")
        String name,
        @NotNull @Email(message = "Write a valid email")
        String email,
        @NotNull(message = "Password cannot be null")
        String password) {
}
