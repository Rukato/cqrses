package com.targa.labs.dev.cqrses.services;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.targa.labs.dev.cqrses.commands.CreateAccountCommand;
import com.targa.labs.dev.cqrses.commands.CreditMoneyCommand;
import com.targa.labs.dev.cqrses.commands.DebitMoneyCommand;
import com.targa.labs.dev.cqrses.domain.AccountCreationDTO;
import com.targa.labs.dev.cqrses.domain.BankAccount;
import com.targa.labs.dev.cqrses.domain.MoneyAmountDTO;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountCommandService {
    private final CommandGateway gateway;

    public CompletableFuture<BankAccount> createAccount(AccountCreationDTO account){
        return gateway.send(new CreateAccountCommand(
                                    UUID.randomUUID(), 
                                    account.getInitialBalance(), 
                                    account.getOwner()
                                )
                            );
    }

    public CompletableFuture<String> creditMoneyToAccount(String accountId, MoneyAmountDTO moneyCreditDTO){
        return gateway.send(new CreditMoneyCommand(
                                    UUID.fromString(accountId), 
                                    moneyCreditDTO.getAmount()
                                )
                            );
    }

    public CompletableFuture<String> debitMoneyFromAccount(String accountId, MoneyAmountDTO moneyDebitDTO) {
        return gateway.send(new DebitMoneyCommand(
                                                UUID.fromString(accountId),
                                                moneyDebitDTO.getAmount()
                                            )
                                        );
    }
}