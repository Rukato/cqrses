package com.targa.labs.dev.cqrses.events;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountCreatedEvent {
    
    private UUID id;
    private BigDecimal initialBalance;
    private String owner;
}