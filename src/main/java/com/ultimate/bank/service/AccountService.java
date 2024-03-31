package com.ultimate.bank.service;

import com.ultimate.bank.domain.Account;
import com.ultimate.bank.domain.Transaction;
import com.ultimate.bank.exception.LimitExceedException;
import com.ultimate.bank.exception.NotFoundException;
import com.ultimate.bank.model.account.OperationRequest;
import com.ultimate.bank.model.account.TransferRequest;
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

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<Void> transaction(OperationRequest request, JwtAuthenticationToken token) {

        Account account = accountRepository.findByUserCPF(request.hashedCPF())
                                           .orElseThrow(() -> new NotFoundException("Account not found."));

        if (account.getUser().getCPF().equals(token.getName())) {

            if (request.type() == TransactionType.Deposit) {
                if (account.getBalance() + request.amount() > 999999) {
                    throw new LimitExceedException("Deposit limit exceeded.");
                }
                account.deposit(request.amount());
            }

            if (request.type() == TransactionType.Withdraw) {
                if (account.getBalance() < request.amount()) {
                    throw new LimitExceedException("Insufficient funds.");
                }

                account.withdraw(request.amount());
            }

            accountRepository.save(account);

            Transaction transaction = new Transaction();
            transaction.createTransaction(account, request.type(), request.amount(), request.description());
            transactionRepository.save(transaction);

            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    public ResponseEntity<Void> transfer(TransferRequest request, JwtAuthenticationToken token) {

        String hashedReceiverCPF = HashUtil.hashCPF(request.receiverCPF());

        Account senderAccount = accountRepository.findByUserCPF(request.hashedCPF())
                                                 .orElseThrow(() -> new NotFoundException("Account not found."));
        Account receiverAccount = accountRepository.findByUserCPF(hashedReceiverCPF)
                                                   .orElseThrow(
                                                           () -> new NotFoundException("Receiver account not found" +
                                                                                               "."));

        if (senderAccount.getUser().getCPF().equals(token.getName())) {
            if (senderAccount.getBalance() < request.amount()) {
                throw new LimitExceedException("Insufficient funds.");
            }

            senderAccount.transfer(request.amount(), receiverAccount);

            accountRepository.save(senderAccount);
            accountRepository.save(receiverAccount);

            Transaction sendertransaction = new Transaction();
            sendertransaction.senderTransferTransaction(senderAccount, receiverAccount, TransactionType.Transfer,
                                                        request.amount(),
                                                        request.description());
            transactionRepository.save(sendertransaction);

            Transaction receiverTransaction = new Transaction();
            receiverTransaction.receiverTransferTransaction(receiverAccount, senderAccount, TransactionType.Transfer,
                                                            request.amount(),
                                                            request.description());
            transactionRepository.save(receiverTransaction);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
