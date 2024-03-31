package com.ultimate.bank.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "accounts")
@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_CPF", referencedColumnName = "CPF", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private int balance;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    private List<Transaction> transactions;

    public void createAccount(User user) {
        this.user = user;
        this.creationDate = LocalDateTime.now();
    }

    public void deposit(int value) {
        this.balance += value;
    }

    public void withdraw(int value) {
        this.balance -= value;
    }

    public void transfer(int value, Account targetAccount) {
        this.balance -= value;
        targetAccount.deposit(value);
    }
}
