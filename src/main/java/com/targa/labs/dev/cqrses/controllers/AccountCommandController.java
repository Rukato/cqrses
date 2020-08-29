package com.targa.labs.dev.cqrses.controllers;

import java.util.concurrent.CompletableFuture;

import com.targa.labs.dev.cqrses.domain.AccountCreationDTO;
import com.targa.labs.dev.cqrses.domain.BankAccount;
import com.targa.labs.dev.cqrses.domain.MoneyAmountDTO;
import com.targa.labs.dev.cqrses.services.AccountCommandService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/accounts")
@Api(value = "Bank Account Commands", description = "Bank Account Commands Api")
@AllArgsConstructor
public class AccountCommandController {
    private final AccountCommandService accountCommandService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompletableFuture<BankAccount> createAccount(@RequestBody AccountCreationDTO creationDTO){
        return accountCommandService.createAccount(creationDTO);
    }

    @PutMapping(value = "/credit/{accountId}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value="accountId") String accountId, 
                                                    @RequestBody MoneyAmountDTO moneyCreditDTO){
        return accountCommandService.creditMoneyToAccount(accountId, moneyCreditDTO);
    }

    @PutMapping(value = "/debit/{accountId}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value="accountId") String accountId, 
                                                    @RequestBody MoneyAmountDTO moneyDebitDTO){
        return accountCommandService.debitMoneyFromAccount(accountId, moneyDebitDTO);
    }

    
}