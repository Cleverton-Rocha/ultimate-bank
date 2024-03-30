package com.ultimate.bank.model.account;

import com.ultimate.bank.model.transaction.TransactionType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record OperationRequest(
        @NotNull
        @Length(min = 11, max = 11, message = "CPF must have 11 characters.")
        @Pattern(regexp = "^[0-9]\\d*$", message = "CPF must have only numbers, without special characters or letters.")
        String CPF,

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be positive")
        @Max(value = 999999, message = "Amount must be less than 1000000")
        int amount,

        @NotNull(message = "Type cannot be null")
        TransactionType type,

        @NotNull(message = "Description cannot be null")
        String description) {
}
