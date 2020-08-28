package com.targa.labs.dev.cqrses.aggregate;

import java.math.BigDecimal;
import java.util.UUID;

import com.targa.labs.dev.cqrses.commands.CreateAccountCommand;
import com.targa.labs.dev.cqrses.commands.CreditMoneyCommand;
import com.targa.labs.dev.cqrses.commands.DebitMoneyCommand;
import com.targa.labs.dev.cqrses.events.AccountCreatedEvent;
import com.targa.labs.dev.cqrses.events.MoneyCreditedEvent;
import com.targa.labs.dev.cqrses.events.MoneyDebitedEvent;
import com.targa.labs.dev.cqrses.exceptions.InsufficientBalanceException;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
public class BankAccountAggregate {
    
    @AggregateIdentifier
    private UUID id;
    private BigDecimal balance;
    private String owner;

    @CommandHandler
    public BankAccountAggregate(CreateAccountCommand command) {
        
        AggregateLifecycle.apply( AccountCreatedEvent.builder()
                        .id(command.getAccountId())
                        .initialBalance(command.getInitialBalance())
                        .owner(command.getOwner())
                    .build()
        );
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.id = event.getId();
        this.owner = event.getOwner();
        this.balance = event.getInitialBalance();
    }

    @CommandHandler
    public void handle(CreditMoneyCommand command){
        AggregateLifecycle.apply(
            new MoneyCreditedEvent(
                command.getAccountId(), 
                command.getCreditAmount()
            )
        );
    }

    @EventSourcingHandler
    public void on(MoneyCreditedEvent event) {
        this.balance = this.balance.add(event.getCreditAmount());
    }

    @CommandHandler
    public void handle(DebitMoneyCommand command) {
        AggregateLifecycle.apply(
            new MoneyDebitedEvent(
                command.getAccountId(), 
                command.getDebitAmount()
            )
        );
    }

    @EventSourcingHandler
    public void on (MoneyDebitedEvent event) throws InsufficientBalanceException {
        if( this.balance.compareTo(event.getDebitAmount()) < 0) {
            throw new InsufficientBalanceException(event.getId(), event.getDebitAmount());
        }
        this.balance = this.balance.subtract(event.getDebitAmount());
    }
}