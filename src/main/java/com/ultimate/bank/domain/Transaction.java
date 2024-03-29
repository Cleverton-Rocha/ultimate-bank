package com.ultimate.bank.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ultimate.bank.model.transaction.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "transactions")
@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String description;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    public void createTransaction(Account account, TransactionType type, int amount, String description) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = LocalDateTime.now();
    }
}
