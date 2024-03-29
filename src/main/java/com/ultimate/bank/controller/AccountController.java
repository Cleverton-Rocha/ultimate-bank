package com.ultimate.bank.controller;

import com.ultimate.bank.model.account.OperationRequest;
import com.ultimate.bank.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bank/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/transaction")
    public void transaction(@RequestBody OperationRequest request) {
        accountService.operation(request);
    }
}
