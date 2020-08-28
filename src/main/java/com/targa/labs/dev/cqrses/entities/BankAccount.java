package com.targa.labs.dev.cqrses.entities;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class BankAccount {
    @Id
    private UUID id;
    private String owner;
    private BigDecimal balance;
}