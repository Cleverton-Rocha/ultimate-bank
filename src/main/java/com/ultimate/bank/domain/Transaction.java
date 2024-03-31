package com.ultimate.bank.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ultimate.bank.model.transaction.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;

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

    @Column(name = "sender_account_name", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String from;

    @Column(name = "receiver_account_name", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String to;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String description;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    public void createTransaction(Account account, TransactionType type, int amount,
                                  String description) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = LocalDateTime.now();
    }

    public void senderTransferTransaction(Account senderAccount, Account receiverAccount, TransactionType type,
                                          int amount,
                                          String description) {
        this.account = senderAccount;
        this.to = receiverAccount.getUser().getFirstName() + " " + receiverAccount.getUser().getLastName();
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = LocalDateTime.now();
    }

    public void receiverTransferTransaction(Account receiverAccount, Account senderAccount, TransactionType type,
                                            int amount,
                                            String description) {
        this.account = receiverAccount;
        this.from = senderAccount.getUser().getFirstName() + " " + senderAccount.getUser().getLastName();
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = LocalDateTime.now();
    }
}
