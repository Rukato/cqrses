package com.targa.labs.dev.cqrses.domain;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class AccountCreationDTO {
    private final BigDecimal initialBalance;
    private final String owner;
}
