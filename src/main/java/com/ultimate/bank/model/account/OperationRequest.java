package com.ultimate.bank.model.account;

import com.ultimate.bank.model.transaction.TransactionType;

public record OperationRequest(String CPF, int amount, TransactionType type,
                               String description) {
}
