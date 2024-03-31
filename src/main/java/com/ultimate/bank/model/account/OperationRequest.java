package com.ultimate.bank.model.account;

import com.ultimate.bank.model.transaction.TransactionType;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record OperationRequest(
        @NotNull(message = "teste")
        String hashedCPF,

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be positive")
        @Max(value = 999999, message = "Amount must be less than 1000000")
        int amount,

        @NotNull(message = "Type cannot be null")
        TransactionType type,

        @NotNull(message = "Description cannot be null")
        String description) {
}
