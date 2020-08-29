package com.targa.labs.dev.cqrses.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.targa.labs.dev.cqrses.domain.BankAccount;
import com.targa.labs.dev.cqrses.services.AccountQueryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value ="/accounts")
@Api(value="Bank Account Queries", description ="Bank Account Query Events Api")
@AllArgsConstructor
public class AccountQueryController {
    private final AccountQueryService queryService;

    @GetMapping("/{accountId}")
    public CompletableFuture<BankAccount> findById(@PathVariable("accountId") String accountId){
        return queryService.findById(accountId);
    }

    @GetMapping("/{accountId}/events")
    public List<Object> listEventsForAccount(@PathVariable(value="accountId") String accountId){
        return queryService.listEventsForAccount(accountId);
    }
}