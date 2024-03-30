package com.ultimate.bank.service;

import com.ultimate.bank.domain.Account;
import com.ultimate.bank.domain.Transaction;
import com.ultimate.bank.exception.LimitExceedException;
import com.ultimate.bank.exception.NotFoundException;
import com.ultimate.bank.exception.StatusCodeException;
import com.ultimate.bank.model.account.OperationRequest;
import com.ultimate.bank.model.transaction.TransactionType;
import com.ultimate.bank.repository.AccountRepository;
import com.ultimate.bank.repository.TransactionRepository;
import com.ultimate.bank.util.HashUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

    public void operation(OperationRequest request,
                          JwtAuthenticationToken token) {

        String hashedCPF = HashUtil.hashCPF(request.CPF());

        Account account = accountRepository.findByUserCPF(hashedCPF)
                .orElseThrow(() -> new NotFoundException("Account not found."));

        if (account.getUser().getCPF().equals(token.getName())) {
            if (request.type() == TransactionType.Deposit) {
                if (account.getBalance() + request.amount() > 999999) {
                    throw new LimitExceedException("Deposit limit exceeded.");
                }
                account.deposit(request.amount());
            }

            if (request.type() == TransactionType.Withdraw) {
                if (account.getBalance() < request.amount() - account.getBalance()) {
                    throw new LimitExceedException("Insufficient funds.");
                }

                account.withdraw(request.amount());
            }

            accountRepository.save(account);

            Transaction transaction = new Transaction();
            transaction.createTransaction(account, request.type(),
                    request.amount(),
                    request.description());

            transactionRepository.save(transaction);
        } else {
            throw new StatusCodeException(HttpStatus.FORBIDDEN.value());
        }

        ResponseEntity.status(HttpStatus.OK).build();
    }
}
