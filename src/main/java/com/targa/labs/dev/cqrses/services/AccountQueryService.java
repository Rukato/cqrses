package com.targa.labs.dev.cqrses.services;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.targa.labs.dev.cqrses.domain.BankAccount;
import com.targa.labs.dev.cqrses.queries.FindBankAccountQuery;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountQueryService {
    private final QueryGateway gateway;
    private final EventStore store;

    public CompletableFuture<BankAccount> findById(String accountId) {
        return gateway.query(new FindBankAccountQuery(
                                UUID.fromString(accountId)), 
                                ResponseTypes.instanceOf(BankAccount.class)
                            );
    }

    public List<Object> listEventsForAccount(String accountId){
        return store.readEvents(
                    UUID.fromString(accountId).toString())
                    .asStream()
                    .map(Message::getPayload)
                    .collect(Collectors.toList());      
    }
}