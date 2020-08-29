package com.targa.labs.dev.cqrses.domain;

import java.math.BigDecimal;
import java.util.UUID;

import com.targa.labs.dev.cqrses.aggregate.BankAccountAggregate;
import com.targa.labs.dev.cqrses.commands.CreateAccountCommand;
import com.targa.labs.dev.cqrses.commands.CreditMoneyCommand;
import com.targa.labs.dev.cqrses.commands.DebitMoneyCommand;
import com.targa.labs.dev.cqrses.events.AccountCreatedEvent;
import com.targa.labs.dev.cqrses.events.MoneyCreditedEvent;
import com.targa.labs.dev.cqrses.events.MoneyDebitedEvent;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountTest {
    private static final String customerName = "Nebrass";

    private FixtureConfiguration<BankAccountAggregate> fixture;
    private UUID id;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(BankAccountAggregate.class);
        id = UUID.randomUUID();
    }

    @Test
    public void should_dispatch_accountcreated_event_when_createaccount_command() {
        fixture.givenNoPriorActivity()
                .when(new CreateAccountCommand(
                        id,
                        BigDecimal.valueOf(1000),
                        customerName)
                )
                .expectEvents(AccountCreatedEvent.builder()
                                .id(id)
                                .initialBalance(BigDecimal.valueOf(1000))
                                .owner(customerName)
                                .build()
                            );
    }

    @Test
    public void should_dispatch_moneycredited_event_when_balance_is_lower_than_debit_amount() {
        fixture.given(AccountCreatedEvent.builder()
                        .id(id)
                        .initialBalance(BigDecimal.valueOf(1000))
                        .owner(customerName)
                        .build()
                    )
                .when(new CreditMoneyCommand(
                        id,
                        BigDecimal.valueOf(100))
                )
                .expectEvents(new MoneyCreditedEvent(
                        id,
                        BigDecimal.valueOf(100))
                );
    }

    @Test
    public void should_dispatch_moneydebited_event_when_balance_is_upper_than_debit_amount() {
        fixture.given(AccountCreatedEvent.builder()
                        .id(id)
                        .initialBalance(BigDecimal.valueOf(1000))
                        .owner(customerName)
                        .build())
                .when(new DebitMoneyCommand(
                        id, 
                        BigDecimal.valueOf(100)))
                .expectEvents(new MoneyDebitedEvent(
                        id, 
                        BigDecimal.valueOf(100)));
    }

    @Test
    public void should_not_dispatch_event_when_balance_is_lower_than_debit_amount() {
        fixture.given(AccountCreatedEvent.builder()
                        .id(id)
                        .initialBalance(BigDecimal.valueOf(1000))
                        .owner(customerName)
                        .build())
                .when(new DebitMoneyCommand(
                        id, 
                        BigDecimal.valueOf(5000)))
                .expectNoEvents();
    }
}