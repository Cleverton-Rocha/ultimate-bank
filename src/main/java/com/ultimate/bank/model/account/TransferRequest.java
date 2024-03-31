package com.ultimate.bank.model.account;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record TransferRequest(
        @NotNull
        String hashedCPF,

        @NotNull
        @Size(min = 11, max = 11, message = "CPF must have 11 characters.")
        @Pattern(regexp = "^[0-9]\\d*$", message = "CPF must have only numbers, without special characters or letters.")
        String receiverCPF,

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be positive")
        @Max(value = 999999, message = "Amount must be less than 1000000")
        int amount,

        @NotNull(message = "Description cannot be null")
        String description
) {
}
