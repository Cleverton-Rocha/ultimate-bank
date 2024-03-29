package com.ultimate.bank.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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
    private double balance;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public void createAccount(User user) {
        this.user = user;
        this.creationDate = LocalDateTime.now();
    }
}
