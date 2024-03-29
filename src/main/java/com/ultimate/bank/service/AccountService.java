package com.ultimate.bank.service;

import com.ultimate.bank.domain.Account;
import com.ultimate.bank.domain.Transaction;
import com.ultimate.bank.exception.InsufficientFundsException;
import com.ultimate.bank.exception.NotFoundException;
import com.ultimate.bank.model.account.OperationRequest;
import com.ultimate.bank.model.transaction.TransactionType;
import com.ultimate.bank.repository.AccountRepository;
import com.ultimate.bank.repository.TransactionRepository;
import com.ultimate.bank.util.HashUtil;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void operation(OperationRequest request) {

        String hashedCPF = HashUtil.hashCPF(request.CPF());

        Account account = accountRepository.findByUserCPF(hashedCPF)
                .orElseThrow(() -> new NotFoundException("Account not found."));

        if (request.type() == TransactionType.Deposit) {
            account.deposit(request.amount());
        }

        if (request.type() == TransactionType.Withdraw) {
            if (account.getBalance() < request.amount() - account.getBalance()) {
                throw new InsufficientFundsException("Insufficient funds.");
            }

            account.withdraw(request.amount());
        }

        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.createTransaction(account, request.type(),
                request.amount(),
                request.description());

        transactionRepository.save(transaction);
    }


}
