package com.targa.labs.dev.cqrses.projections;

import java.util.Optional;

import com.targa.labs.dev.cqrses.domain.BankAccount;
import com.targa.labs.dev.cqrses.domain.BankAccountRepository;
import com.targa.labs.dev.cqrses.events.AccountCreatedEvent;
import com.targa.labs.dev.cqrses.events.MoneyCreditedEvent;
import com.targa.labs.dev.cqrses.events.MoneyDebitedEvent;
import com.targa.labs.dev.cqrses.exceptions.AccountNotFoundException;
import com.targa.labs.dev.cqrses.queries.FindBankAccountQuery;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BankAccountProjection {
    
    private final BankAccountRepository repo;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.debug("Handling a Bank account creation command {}", event.getId());
        BankAccount account = new BankAccount(event.getId(), event.getOwner(), event.getInitialBalance());
        repo.save(account);
    }

    @EventHandler
    public void on(MoneyCreditedEvent event) throws AccountNotFoundException {
        log.debug("Handling a Bank account credit command {}", event.getId());
        Optional<BankAccount> optionalBankAccount = repo.findById(event.getId());
        if(optionalBankAccount.isPresent()){
            BankAccount account = optionalBankAccount.get();
            account.setBalance(account.getBalance().add(event.getCreditAmount()));
            repo.save(account);
        } else {
            throw new AccountNotFoundException(event.getId());
        }
    }

    @EventHandler
    public void on(MoneyDebitedEvent event) throws AccountNotFoundException {
        log.debug("Handling a Bank account debited command {}", event.getId());
        Optional<BankAccount> optionalBankAccount = repo.findById(event.getId());
        if(optionalBankAccount.isPresent()){
            BankAccount account = optionalBankAccount.get();
            account.setBalance(account.getBalance().subtract(event.getDebitAmount()));
            repo.save(account);
        } else {
            throw new AccountNotFoundException(event.getId());
        }
    }

    @QueryHandler
    public BankAccount handle(FindBankAccountQuery query) {
        log.debug("Handling FindBankAccountQuery query {}", query);
        return repo.findById(query.getAccountId()).orElse(null);
    }

}