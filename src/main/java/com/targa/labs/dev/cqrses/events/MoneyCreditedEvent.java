package com.targa.labs.dev.cqrses.events;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Value;

@Value
public class MoneyCreditedEvent {
    
    private final UUID id;
    private final BigDecimal creditAmount;
}